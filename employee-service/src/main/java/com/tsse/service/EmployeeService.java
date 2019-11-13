package com.tsse.service;

import org.springframework.beans.factory.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tsse.client.DepartmentInternalClientStrategy;
import com.tsse.client.IDepartmentEnquiryStrategy;
import com.tsse.factory.ClientFactory;
import com.tsse.model.Department;
import com.tsse.model.Employee;
import com.tsse.repository.EmployeeRepository;
import com.tsse.validation.model.EmployeeValidationDTO;

@Service
@Slf4j
//@CacheConfig(cacheNames={"employees"})
public class EmployeeService implements IEmployeeService {

	public EmployeeService(ClientFactory factory, EmployeeRepository employeeRepository) {
		super();
		this.factory = factory;
		this.employeeRepository = employeeRepository;
	}

	//private DepartmentRestClient departmentRestClient;

	private EmployeeRepository employeeRepository;

	private ClientFactory factory;
	
	@Cacheable(value = "employees", key = "#id", cacheManager = "timeOutCacheManager")
	@Transactional(readOnly = true)
	public Employee getEmployeeAndDepartment(Long id) throws InterruptedException, ExecutionException {
 		log.info("getEmployeeAndDepartment - start getting employee with id {}", id);
		CompletableFuture<Employee> emp;
		CompletableFuture<Department> dept;
		emp = employeeRepository.findByEmpId(id);
		IDepartmentEnquiryStrategy client = factory.getClient("");
		dept = client.getDepartment(id);

		// EmployeeDO empDo = null;
		Department deptartment = null;
		Employee employee = null;
		deptartment = dept.get();

		log.info("getEmployeeAndDepartment - Department retrieved from REST call is {}", dept);

		if (null != emp.get()) {
			employee = emp.get();
		} else {
			throw new EntityNotFoundException("Employee not found");
		}

		log.info("getEmployeeAndDepartment - Employee retrieved from db with id {}: {}", id, emp);
		employee.setDepartment(deptartment);
		// empDo = EmployeeDO.builder().department(deptartment).emp(employee).build();
		log.info("getEmployeeAndDepartment - employee returned: {}", employee);
		return employee;

	}

	@Override
	@Cacheable(value = "employees", cacheManager = "timeOutCacheManager")
	@Transactional(readOnly = true)
	public List<Employee> getEmployeesAndDepartment(String direction, String orderBy, String useCase)
			throws InterruptedException, ExecutionException {

		log.info("getEmployeesAndDepartment - Start getting all Employees");
		Sort sort = new Sort(Sort.Direction.DESC, orderBy);
		if (direction.equals("ASC")) {
			sort = new Sort(Sort.Direction.ASC, orderBy);
		}

		IDepartmentEnquiryStrategy client = factory.getClient(useCase);
		
		List<Employee> emp = (List<Employee>) employeeRepository.findAll(sort);
		log.info("getEmployeesAndDepartment - Getting all Employees successfully from db");
		
		List<Employee> employees = new ArrayList<>();
		for (Iterator iterator = emp.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			Department deptartment = null;
			CompletableFuture<Department> dept = client.getDepartment(employee.getEmpId());
			deptartment = dept.get();

			employee.setDepartment(deptartment);
			employees.add(employee);
		}
		log.info("getEmployeesAndDepartment - End getting all Employees: {}", employees);
		return employees;
	}

	@Override
	@CachePut(value = "employees", key = "#empId")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW) // need to understand the use
																							// of propagation
	public Employee updateEmployeeAndDepartment(Long empId, Employee updatedEmployee)
			throws InterruptedException, ExecutionException {
		log.info("updateEmployeeAndDepartment - Start updating Employee with id: {}", empId);
		Employee existingEmployee = getEmployeeAndDepartment(empId);
		log.info("updateEmployeeAndDepartment - End updating Employee with id: {} successfully", empId);
		return employeeRepository.save(existingEmployee);

	}

	@Override
	@CachePut(value = "employees")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW) // need to understand the use
																							// of propagation
	public Employee saveEmployee(Employee employee) {
		log.info("saveEmployee - Start creating Employee: {}", employee);
		return employeeRepository.save(employee);
	}
}
