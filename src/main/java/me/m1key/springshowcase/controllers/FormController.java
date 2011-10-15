package me.m1key.springshowcase.controllers;

import me.m1key.springshowcase.domain.Reservation;
import me.m1key.springshowcase.validators.ReservationValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/form")
@SessionAttributes("reservation")
public class FormController {

	@Autowired
	private ReservationValidator validator;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		return "form";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(Model model,
			@ModelAttribute("reservation") Reservation reservation,
			BindingResult result, SessionStatus status) {
		validator.validate(reservation, result);
		if (result.hasErrors()) {
			model.addAttribute("reservation", reservation);
			return "form";
		} else {
			status.setComplete();
			return "redirect:form/success";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/success")
	public String displaySuccess() {
		return "success";
	}

}
