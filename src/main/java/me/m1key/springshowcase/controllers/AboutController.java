package me.m1key.springshowcase.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/about")
public class AboutController {

	@RequestMapping(method = RequestMethod.GET)
	public String handle() {
		System.out.println("Inside AboutController.");
		return "about";
	}

}
