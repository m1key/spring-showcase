package me.m1key.springshowcase.editors;

import java.beans.PropertyEditorSupport;

import me.m1key.springshowcase.to.Gender;

import org.apache.commons.lang.StringUtils;

public class GenderEditor extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		if (getValue() == null) {
			return null;
		} else {
			Gender gender = (Gender) getValue();
			return gender.toString();
		}
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
			setValue("");
		} else if (text.equalsIgnoreCase("m")) {
			setValue(Gender.MALE);
		} else if (text.equalsIgnoreCase("f")) {
			setValue(Gender.FEMALE);
		} else if (text.equalsIgnoreCase("o")) {
			setValue(Gender.OTHER);
		}
	}

}
