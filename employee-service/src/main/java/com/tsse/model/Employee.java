package com.tsse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.*;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tsse.annotation.EmployeeCustomAnnotation;

import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "employee")
@JsonRootName("employee")
//@EmployeeCustomAnnotation
public class Employee extends ResourceSupport { //Resource support class is used fro hypermedia
	
	@Id
    @GeneratedValue//(strategy = GenerationType.AUTO)
    private Long empId;

    @Column//(nullable = false)
    //@NotNull(message = "firstname cant be null")
    @Size(min=2, max=30)
    private String firstName;

    @Column//(nullable = false)
    //@NotNull(message = "lastname cant be null")
    @Size(min=2, max=30)
    private String lastName;

    @Column
    @Min(18)
    private int age;
    
    @Column(nullable = true)
    @Email
    private String email;

    @Transient
    private Department department;
    
	/*public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long id) {
		this.empId = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "Employee [id=" + empId + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", email=" + email + "]";
	}*/


}
