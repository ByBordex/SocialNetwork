package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entites.User;

@Component
public class PhotoValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		MultipartFile photo = (MultipartFile) target;
		if (photo.getSize() > 1048576) {
			errors.rejectValue("photo", "Error.photo.tooBig");
		}
	}
	
}