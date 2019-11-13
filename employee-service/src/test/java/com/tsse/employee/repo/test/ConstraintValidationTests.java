package com.tsse.employee.repo.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tsse.model.Employee;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConstraintValidationTests extends EmployeeRepositoryIntegrationTest {
	
	private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
	public void addInvalidEmployeeAge() {
		Employee emp = constructEmployee(null, 17, "Alex", "John"); // age should be +18
		Set<ConstraintViolation<Employee>> violations = validator.validate(emp);
        assertFalse(violations.isEmpty());
		
	}

    @Test
	public void addInvalidEmployeeFirstOrLastName() {
		Employee emp = constructEmployee(null, 18, null, null); // First name shouldnt be null
		Set<ConstraintViolation<Employee>> violations = validator.validate(emp);
        assertFalse(violations.isEmpty());
		
	}
    
    
    @Test
	public void addValidEmployeee() {
		Employee emp = constructEmployee(null, 18, "Alex", "John"); // Valid EMployee
		Set<ConstraintViolation<Employee>> violations = validator.validate(emp);
        assertTrue(violations.isEmpty());
		
	}
}
