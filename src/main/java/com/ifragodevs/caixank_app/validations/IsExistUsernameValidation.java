package com.ifragodevs.caixank_app.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ifragodevs.caixank_app.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


@Component
public class IsExistUsernameValidation implements ConstraintValidator<IsExistUsername, String>{
	
	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(userService == null) {
			return true;
		}
		return userService.existsByUsername(value);
	}
}

