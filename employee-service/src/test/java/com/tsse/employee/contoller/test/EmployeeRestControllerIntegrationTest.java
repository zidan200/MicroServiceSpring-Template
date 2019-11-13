package com.tsse.employee.contoller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.tsse.controller.EmployeeController;
import com.tsse.employee.security.config.test.SpringSecurityWebAuxTestConfig;
import com.tsse.model.Department;
import com.tsse.model.Employee;
import com.tsse.service.IEmployeeService;

import org.springframework.web.context.*;
import org.springframework.test.web.servlet.setup.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@Import(SpringSecurityWebAuxTestConfig.class)
public class EmployeeRestControllerIntegrationTest {

	/**
	 * need to make security enabled = false;
	 *//*

	@Autowired
	private WebApplicationContext context;*/

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IEmployeeService service;

	private Employee constructEmployee(long id, int age, String firstName, String lastName) {
		Employee employee = new Employee();
		employee.setEmpId(id);
		employee.setAge(age);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);

		return employee;
	}

	@Before
	public void setUp() {
		/*
		 * mvc = MockMvcBuilders.webAppContextSetup(context) .alwaysDo(print())
		 * .apply(springSecurity()) .build();
		 */

		Employee alex = constructEmployee(3L, 18, "Alex", "John");
		List<Employee> emps = new ArrayList<>();

		Department dept = new Department();
		dept.setName("Software dept.");
		alex.setDepartment(dept);

		emps.add(alex);
		// given(service.getEmployeesAndDepartment()).willReturn(emps);

		try {
			Mockito.when(service.getEmployeeAndDepartment(3L)).thenReturn(alex);
		} catch (EntityNotFoundException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Mockito.when(service.getEmployeesAndDepartment("ASC", "firstName")).thenReturn(emps);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// write test cases here
	@Test
	@WithUserDetails("Basic User")
	public void givenEmployees_whenGetEmployee_thenReturnJsonObject() throws Exception {

		mvc.perform(get("/api/employees/3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				// .andExpect(jsonPath("$", hasSize(1)));
				.andExpect(jsonPath("$.firstName", is("Alex")));
	}

	@Test
	@WithUserDetails("Basic User")
	public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {

		mvc.perform(get("/api/employees?orderBy=firstName&direction=ASC").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				// .andExpect(jsonPath("$", hasSize(1)));
				.andExpect(jsonPath("$._embedded.employeeList[0].firstName", is("Alex")));
	}

	@Test
	public void authenticatedWithFraudUser() throws Exception {

		mvc.perform(get("/api/employees").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
		// .andExpect(jsonPath("$", hasSize(1)));
		// .andExpect(jsonPath("$._embedded.employeeDOList[0].employee.firstName",
		// is("Alex")));
	}
}