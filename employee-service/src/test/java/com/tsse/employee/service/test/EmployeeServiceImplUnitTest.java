package com.tsse.employee.service.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.tsse.client.DepartmentInternalClientStrategy;
import com.tsse.model.Department;
import com.tsse.model.Employee;
import com.tsse.repository.EmployeeRepository;
import com.tsse.service.IEmployeeService;
import com.tsse.service.EmployeeService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplUnitTest {
 
    /*@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImp(departmentRestClient, employeeRepository);
        }
    }*/
    
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
    	employeeService = new EmployeeService(departmentRestClient, employeeRepository);
        Employee alex = constructEmployee(3L, 18, "Alex", "John");
        List<Employee> emps = new ArrayList<>();
        emps.add(alex);
        Sort sort = new Sort(Sort.Direction.ASC, "firstName");
		
        Mockito.when(employeeRepository.findAll(sort))
          .thenReturn(emps);
        
        CompletableFuture<Department> deptFuture = new CompletableFuture<>();
        Department dept = new Department();
        dept.setName("Software dept.");
        deptFuture.complete(dept);
        Mockito.when(departmentRestClient.getDepartment(alex.getEmpId()))
        .thenReturn(deptFuture);
    }
 
    
    private IEmployeeService employeeService;
 
    @Mock
    private EmployeeRepository employeeRepository;
 
    @Mock
    private DepartmentInternalClientStrategy departmentRestClient;
    // write test cases here
    
    @Test
    @DisplayName("Getting all employees successful..")
    public void givenEmployee_whenGetAllEmployees_thenAllEmployeesShouldBeRetreived() {
        List<Employee> found=new ArrayList<>();
		try {
			found = employeeService.getEmployeesAndDepartment("ASC","firstName");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
         assertThat(found.size())
          .isEqualTo(1);
         
         //another assertions should be done.
     }
}