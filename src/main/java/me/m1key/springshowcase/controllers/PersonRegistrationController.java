package me.m1key.springshowcase.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.m1key.springshowcase.editors.GenderEditor;
import me.m1key.springshowcase.to.Gender;
import me.m1key.springshowcase.to.PersonTo;
import me.m1key.springshowcase.validators.PersonRegistrationValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

	private static final String REDIRECT_TO_FORM = "redirect:../personRegistration";
	private static final String SUCCESS_PAGE = "registrationSuccess";
	private static final String PERSON_TO = "personTo";
	private static final String REDIRECT_TO_SUCCESS_PAGE = "redirect:personRegistration/registrationSuccess";
	private static final String REGISTRATION_GENDER_FORM = "registrationGenderForm";
	private static final String REGISTRATION_NAME_FORM = "registrationNameForm";
	private static final String REDIRECT_TO_HOMEPAGE = "redirect:/";

	private PersonRegistrationValidator validator;

	private Logger log = LoggerFactory
			.getLogger(PersonRegistrationController.class);

	@Autowired
	public PersonRegistrationController(PersonRegistrationValidator validator) {
		super();
		this.validator = validator;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Gender.class, new GenderEditor());
	}

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
			validator.validate(personTo, result);
			if (result.hasErrors()) {
				return pageForms.get(currentPage);
			} else {
				log.info("Registration finished for person [{}: {}].",
						personTo.getGender(), personTo.getName());
				personTo.setRegistrationComplete(true);
				return REDIRECT_TO_SUCCESS_PAGE;
			}
		} else {
			int targetPage = WebUtils.getTargetPage(request, "_target",
					currentPage);
			if (userClickedPrevious(currentPage, targetPage)) {
				return pageForms.get(targetPage);
			} else {
				switch (currentPage) {
				case 0:
					validator.validateName(personTo, result);
					break;
				}

				if (result.hasErrors()) {
					return pageForms.get(currentPage);
				} else {
					return pageForms.get(targetPage);
				}
			}
		}
	}

	private boolean userClickedPrevious(int currentPage, int targetPage) {
		return targetPage < currentPage;
	}

	@ModelAttribute("genders")
	public Map<String, String> getGenders() {
		Map<String, String> genders = new LinkedHashMap<String, String>();
		genders.put("", "-- pick one --");
		genders.put("m", "Male");
		genders.put("f", "Female");
		genders.put("o", "Other");
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
