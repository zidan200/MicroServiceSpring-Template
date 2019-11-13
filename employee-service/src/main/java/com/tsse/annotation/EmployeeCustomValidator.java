package com.tsse.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tsse.model.Employee;
import com.tsse.validation.model.EmployeeValidationDTO;

public class EmployeeCustomValidator implements ConstraintValidator<EmployeeCustomAnnotation, EmployeeValidationDTO> {

	@Override
	public void initialize(EmployeeCustomAnnotation employeeCustom) {

	}

	@Override
	public boolean isValid(EmployeeValidationDTO object, ConstraintValidatorContext cxt) {

		if ("ALL".equals(object.getUseCase())) {
			if (object.getFirstName() == null || object.getLastName() == null) {
				return false;
			}
		} else if ("firstName".equals(object.getUseCase())) {
			if (object.getFirstName() == null) {
				return false;
			}
		} else if("lastName".equals(object.getUseCase())) {
			if (object.getLastName() == null) {
				return false;
			}
		}else {
			return true;
		}
		return true;

	}
}
