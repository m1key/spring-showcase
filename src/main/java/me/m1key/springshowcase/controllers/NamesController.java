package me.m1key.springshowcase.controllers;

import java.util.Arrays;

import me.m1key.springshowcase.to.LabelValueTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/names")
public class NamesController {

	private static final Logger log = LoggerFactory
			.getLogger(NamesController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getNames(Model model,
			@RequestParam(required = false) String term) {
		log.debug("getNames(\"{}\")", term);
		model.addAttribute("names", Arrays.asList(names));
		return "jsonNames";
	}

	private LabelValueTo[] names = new LabelValueTo[] {
			new LabelValueTo("Michael", "1"), new LabelValueTo("Mike", "2"),
			new LabelValueTo("Mikey", "3") };

}
