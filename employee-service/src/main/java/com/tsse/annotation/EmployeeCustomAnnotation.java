package com.tsse.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = EmployeeCustomValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeCustomAnnotation {
	String message() default "Firstname or Lastname should be filled";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
