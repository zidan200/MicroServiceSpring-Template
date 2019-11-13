package com.tsse.employee.repo.test;

import static org.assertj.core.api.Assertions.assertThat;



import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.tsse.model.Employee;
import com.tsse.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

	@Autowired
	protected EmployeeRepository employeeRepository;

	// if need to initialize anything before unit tests run
	

	protected Employee constructEmployee(Long id, int age, String firstName, String lastName) {
		Employee employee = new Employee();
		employee.setEmpId(id);
		employee.setAge(age);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);

		return employee;
	}

	// write test cases here
	@Test
	public void whenFindByName_thenReturnEmployee() {
		// given
		/*
		 * Employee alex = constructEmployee(3L, 19, "Alex", "John");
		 * 
		 * entityManager.persist(alex); entityManager.flush();
		 * employeeRepository.save(alex);
		 */
		// when
		Employee found = employeeRepository.findByFirstName("Mahmoud");

		// then
		assertThat(found.getFirstName()).isEqualTo("Mahmoud");
	}

	

}