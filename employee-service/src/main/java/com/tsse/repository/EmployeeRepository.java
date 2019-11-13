package com.tsse.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import com.tsse.model.Employee;


/**
 * Database Access Object for users table.
 * <p/>
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long>
{

	List<Employee> findAll(Sort sort);
	@Async
	CompletableFuture<Employee> findByEmpId(Long empId);
    Employee findByFirstName(String firstName);


}
