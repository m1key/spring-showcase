package me.m1key.springshowcase.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/date")
public class DateController {

	private static final Logger log = LoggerFactory
			.getLogger(DateController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getDate(Model model,
			@RequestParam(required = false) String param,
			@RequestParam(required = false) Date date) {
		log.debug("getDate(), param: {}, date: {}", param, date);
		model.addAttribute("currentDate",
				DateFormat.getTimeInstance().format(new Date()));
		return "date";
	}

}
