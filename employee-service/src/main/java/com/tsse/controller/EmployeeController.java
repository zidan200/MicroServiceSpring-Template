package com.tsse.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tsse.model.Employee;
import com.vodafone.model.security.UserModel;
import com.tsse.service.IEmployeeService;
import com.tsse.validation.model.EmployeeValidationDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/employees")
@Slf4j
public class EmployeeController {

	private IEmployeeService employeeService;

	/**
	 * 
	 * @param employeeService
	 */
	public EmployeeController(IEmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	/**
	 * 
	 * @param empId
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws EntityNotFoundException
	 * @throws GeneralExcpetion
	 */
	@GetMapping(path = { "/{empId}" })
	//@PreAuthorize("hasAnyAuthority('ROLE_READ_EMPLOYEE')")
	public ResponseEntity<Employee> getEmployee(@Valid @PathVariable("empId") final Long empId,
			@Valid EmployeeValidationDTO requestParams)
			throws EntityNotFoundException, InterruptedException, ExecutionException {
		log.info("Employee Controller - Get - for employee with id {} ", empId);
		UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("Prinicpal account number: " + userModel.getAccountNumber());
		Employee emp = employeeService.getEmployeeAndDepartment(empId);
		log.info("Employee Controller - Get - employee to be returned {} ", emp);

		return new ResponseEntity<>(emp, HttpStatus.OK);

	}

	/**
	 * 
	 * @param orderBy
	 * @param direction
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws EntityNotFoundException
	 * @throws GeneralExcpetion
	 * @throws ConstraintsViolationException
	 */
	@GetMapping(params = { "orderBy", "direction" })
	//@PreAuthorize("hasAnyAuthority('ROLE_READ_EMPLOYEE')")
	public Resources<Employee> getEmployees(@RequestParam("orderBy") String orderBy,
			@RequestParam("direction") String direction, @RequestParam("useCase") String useCase)
			throws InterruptedException, ExecutionException {

		/*
		 * if (direction != null &&
		 * !(direction.equals(Direction.ASCENDING.getDirectionCode()) ||
		 * direction.equals(Direction.DESCENDING.getDirectionCode()))) { throw new
		 * ConstraintsViolationException("direction param is invalid"); }
		 * 
		 * if (orderBy != null && !(orderBy.equals(OrderBy.ID.getOrderByCode()) ||
		 * orderBy.equals(OrderBy.FIRSTNAME.getOrderByCode()))) { throw new
		 * ConstraintsViolationException("orderBy param is invalid"); }
		 */

		log.info("getEmployees - start calling employee service, getting Employees & departmnt ordered by {} & {}",
				orderBy, direction);
		UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("Prinicpal account number: " + userModel.getAccountNumber());
		List<Employee> emps = employeeService.getEmployeesAndDepartment(direction, orderBy, useCase);
		log.info("getEmployees - End getting employees {}", emps);
		EmployeeValidationDTO requestParams = new EmployeeValidationDTO();
		for (final Employee emp : emps) {

			Link selfLink = linkTo(methodOn(EmployeeController.class).getEmployee(emp.getEmpId(), requestParams))
					.withSelfRel();
			emp.add(selfLink);
		}

		Link link = linkTo(methodOn(EmployeeController.class).getEmployees(orderBy, direction, useCase)).withSelfRel();
		Resources<Employee> result = new Resources<>(emps, link);
		return result;
		// return new ResponseEntity<>(emps, HttpStatus.OK);
	}

	/**
	 * 
	 * @param employee
	 * @param empId
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws EntityExistsException
	 * @throws EntityNotFoundException
	 * @throws GeneralExcpetion
	 */
	@PutMapping("/{empId}")
	@PreAuthorize("hasAnyAuthority('ROLE_READ_EMPLOYEE')")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void updateResource(@Valid @RequestBody Employee employee, @PathVariable("empId") Long empId)
			throws EntityExistsException, InterruptedException, ExecutionException {
		log.info("updateResource - start updating employee with id {}: {}", empId, employee);
		employeeService.updateEmployeeAndDepartment(empId, employee);
		log.info("updateResource - Employee updated sucessfully with id {}: {}", empId, employee);
	}

	/**
	 * 
	 * @param employee
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws EntityNotFoundException
	 */
	@PostMapping()
	@PreAuthorize("hasAnyAuthority('ROLE_READ_EMPLOYEE')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Resources<Employee> saveResource(@Valid @RequestBody Employee employee, HttpServletRequest request)
			throws EntityNotFoundException, InterruptedException, ExecutionException {
		log.info("saveResource - start creating new employee {}", employee);
		Employee emp = employeeService.saveEmployee(employee);
		log.info("saveResource - Employee created successfully: {}", employee);
		EmployeeValidationDTO requestParams = new EmployeeValidationDTO();
		Link link = linkTo(methodOn(EmployeeController.class).getEmployee(emp.getEmpId(), requestParams)).withSelfRel();
		emp.add(link);
		List<Employee> emps = new ArrayList<>();
		emps.add(emp);
		Resources<Employee> result = new Resources<>(emps, link);
		return result;
	}

}
