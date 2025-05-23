package com.ifragodevs.caixank_app.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;



@Constraint(validatedBy = IsExistUsernameValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsExistUsername {

	String message() default "User not found for the given identifier";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
