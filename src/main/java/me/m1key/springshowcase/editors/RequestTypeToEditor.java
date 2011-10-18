package me.m1key.springshowcase.editors;

import java.beans.PropertyEditorSupport;

import me.m1key.springshowcase.to.RequestTypeTo;

public class RequestTypeToEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(RequestTypeTo.valueOf(text));
	}

}
