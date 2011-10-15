package me.m1key.springshowcase.controllers;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import me.m1key.springshowcase.domain.Reservation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/form")
@SessionAttributes("reservation")
public class FormController {

	private static final String REDIRECT_TO_FORM = "redirect:../form";
	private static final String SUCCESS_PAGE = "success";
	private static final String REDIRECT_TO_SUCCESS = "redirect:form/success";
	private static final String RESERVATION_FORM = "form";
	private static final String RESERVATION = "reservation";

	private static Validator validator;

	public FormController() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		Reservation reservation = new Reservation();
		model.addAttribute(RESERVATION, reservation);
		return RESERVATION_FORM;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(Model model,
			@ModelAttribute(RESERVATION) Reservation reservation,
			BindingResult result) {
		Set<ConstraintViolation<Reservation>> violations = validator
				.validate(reservation);
		for (ConstraintViolation<Reservation> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String message = violation.getMessage();

			result.addError(new FieldError("member", propertyPath, "Invalid "
					+ propertyPath + "(" + message + ")"));
		}

		if (result.hasErrors()) {
			model.addAttribute(RESERVATION, reservation);
			return RESERVATION_FORM;
		} else {
			reservation.setComplete(true);
			return REDIRECT_TO_SUCCESS;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/success")
	public String displaySuccess(Model model, HttpSession session,
			SessionStatus status) {
		if (reservationHasBeenCompleted(session)) {
			model.addAttribute(getReservationFromSession(session));
			status.setComplete();
			return SUCCESS_PAGE;
		} else {
			return REDIRECT_TO_FORM;
		}
	}

	private boolean reservationHasBeenCompleted(HttpSession session) {
		Reservation reservation = getReservationFromSession(session);
		return reservation != null && reservation.isComplete();
	}

	private Reservation getReservationFromSession(HttpSession session) {
		return (Reservation) session.getAttribute(RESERVATION);
	}
}
