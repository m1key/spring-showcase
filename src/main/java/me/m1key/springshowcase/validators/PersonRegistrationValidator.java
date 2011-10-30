package me.m1key.springshowcase.validators;

import me.m1key.springshowcase.to.PersonTo;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonRegistrationValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return PersonTo.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		validateName(target, errors);
		validateGender(target, errors);
	}

	public void validateName(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "required.name");
	}

	private void validateGender(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "gender", "required.gender");
	}

}
