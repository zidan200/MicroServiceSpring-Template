package com.tsse.validation.model;

import javax.validation.constraints.Size;

import com.tsse.annotation.EmployeeCustomAnnotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EmployeeCustomAnnotation
public class EmployeeValidationDTO {

	private String useCase;
	@Size(min=2, max=30)
	private String firstName;
	private String lastName;

}
