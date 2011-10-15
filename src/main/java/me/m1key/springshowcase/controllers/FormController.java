package me.m1key.springshowcase.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import me.m1key.springshowcase.domain.Reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	private static final Logger log = LoggerFactory
			.getLogger(FormController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model,
			@RequestParam(required = false) String userName) {
		log.debug("Setting up form.");
		Reservation reservation = new Reservation();
		reservation.setUserName(userName);
		model.addAttribute(RESERVATION, reservation);
		return RESERVATION_FORM;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(Model model,
			@ModelAttribute(RESERVATION) @Valid Reservation reservation,
			BindingResult result) {
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
