package me.m1key.springshowcase.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.m1key.springshowcase.editors.RequestTypeToEditor;
import me.m1key.springshowcase.to.RequestTypeTo;

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
		binder.registerCustomEditor(RequestTypeTo.class,
				new RequestTypeToEditor());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getDate(Model model,
			@RequestParam(required = false) String param,
			@RequestParam(required = false) Date date,
			@RequestParam(required = false) RequestTypeTo requestType) {
		log.debug("getDate(), param: {}, date: {}", param, date);
		log.debug("getDate(), requestType: {}", requestType);
		model.addAttribute("currentDate",
				DateFormat.getTimeInstance().format(new Date()));
		return "date";
	}

}
