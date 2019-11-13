package com.tsse.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import com.tsse.model.Employee;
import com.tsse.util.enums.Direction;
import com.tsse.util.enums.OrderBy;
import com.tsse.validation.model.EmployeeValidationDTO;

public interface IEmployeeService {

	
	public Employee getEmployeeAndDepartment(Long id) throws EntityNotFoundException, EntityNotFoundException, InterruptedException, ExecutionException;
	
	public List<Employee> getEmployeesAndDepartment(String direction, String orderBy, String useCase) throws InterruptedException, ExecutionException ;

	public Employee updateEmployeeAndDepartment(Long empId, Employee updatedEmployee) throws EntityNotFoundException, InterruptedException, ExecutionException;

	//public Employee updateEmployeeAndDepartment(EmployeeValidationDTO updatedEmployee) throws EntityNotFoundException, InterruptedException, ExecutionException;
	
	public Employee saveEmployee(Employee employee);

}
