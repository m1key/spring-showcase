package me.m1key.springshowcase.controllers;

import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/date")
public class DateController {

	private static final Logger log = LoggerFactory
			.getLogger(DateController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getDate(Model model,
			@RequestParam(required = false) String param) {
		log.debug("getDate(), param: {}", param);
		model.addAttribute("currentDate",
				DateFormat.getTimeInstance().format(new Date()));
		return "date";
	}

}