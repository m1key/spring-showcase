package me.m1key.springshowcase.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/names")
public class NamesController {

	@RequestMapping(method = RequestMethod.GET)
	public List<String> getNames() {
		return Arrays.asList(names);
	}

	private String[] names = new String[] { "Michael", "Mike", "Mikey" };

}
