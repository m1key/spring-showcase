package me.m1key.springshowcase.controllers;

import me.m1key.springshowcase.services.TrivialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/about")
public class AboutController {

	@Autowired
	private TrivialService trivialService;

	@RequestMapping(method = RequestMethod.GET)
	public String handle() {
		System.out.println("Inside AboutController.");
		return "about";
	}

	@ModelAttribute("welcomeMessage")
	public String getWelcomeMessage() {
		System.out.println("Calling getWelcomeMessage");
		return trivialService.getWelcomeMessage();
	}

}
