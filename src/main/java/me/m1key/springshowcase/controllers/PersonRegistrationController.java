package me.m1key.springshowcase.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.m1key.springshowcase.to.Gender;
import me.m1key.springshowcase.to.PersonTo;

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
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/personRegistration")
@SessionAttributes("personTo")
public class PersonRegistrationController {

	private static final String REDIRECT_TO_SUCCESS_PAGE = "redirect:personRegistration/registrationSuccess";

	private static final String REGISTRATION_GENDER_FORM = "registrationGenderForm";

	private static final String REGISTRATION_NAME_FORM = "registrationNameForm";

	private static final String REDIRECT_TO_HOMEPAGE = "redirect:/";

	private Logger log = LoggerFactory
			.getLogger(PersonRegistrationController.class);

	private static final String REDIRECT_TO_FORM = "redirect:../personRegistration";
	private static final String SUCCESS_PAGE = "registrationSuccess";
	private static final String PERSON_TO = "personTo";

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		PersonTo personTo = new PersonTo();
		model.addAttribute(PERSON_TO, personTo);
		return REGISTRATION_NAME_FORM;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute(PERSON_TO) PersonTo personTo, BindingResult result,
			SessionStatus status, @RequestParam("_page") int currentPage,
			Model model) {

		Map<Integer, String> pageForms = new HashMap<Integer, String>();
		pageForms.put(0, REGISTRATION_NAME_FORM);
		pageForms.put(1, REGISTRATION_GENDER_FORM);

		if (userClickedCancel(request)) {
			status.setComplete();
			return REDIRECT_TO_HOMEPAGE;
		} else if (userIsFinished(request)) {
			log.info("Registration finished for person [{}: {}].",
					personTo.getGender(), personTo.getName());
			personTo.setRegistrationComplete(true);
			return REDIRECT_TO_SUCCESS_PAGE;
		} else {
			int targetPage = WebUtils.getTargetPage(request, "_target",
					currentPage);
			if (targetPage < currentPage) {

			}
			return pageForms.get(targetPage);
		}
	}

	@ModelAttribute("genders")
	public Map<Gender, String> getGenders() {
		Map<Gender, String> genders = new HashMap<Gender, String>();
		genders.put(Gender.MALE, "Male");
		genders.put(Gender.FEMALE, "Female");
		genders.put(Gender.OTHER, "Other");
		return genders;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registrationSuccess")
	public String displaySuccess(Model model, HttpSession session,
			SessionStatus status) {
		if (registrationHasBeenCompleted(session)) {
			model.addAttribute(getRegistrationFromSession(session));
			status.setComplete();
			return SUCCESS_PAGE;
		} else {
			return REDIRECT_TO_FORM;
		}
	}

	private boolean userIsFinished(HttpServletRequest request) {
		return request.getParameter("_finish") != null;
	}

	private boolean userClickedCancel(HttpServletRequest request) {
		return request.getParameter("_cancel") != null;
	}

	private boolean registrationHasBeenCompleted(HttpSession session) {
		PersonTo personTo = getRegistrationFromSession(session);
		return personTo != null && personTo.isRegistrationComplete();
	}

	private PersonTo getRegistrationFromSession(HttpSession session) {
		return (PersonTo) session.getAttribute(PERSON_TO);
	}

}
