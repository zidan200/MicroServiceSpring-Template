package com.tsse.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsse.model.Department;

import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {

	
		@GetMapping(path = "/{deptId}")
		//@PreAuthorize("hasAnyAuthority('ROLE_READ_DEPARTMENT')")
		public Department getDepartment(@Valid @PathVariable("deptId") final Long deptId) {
			Department dept = new Department();
			if(deptId == 1L) {
				dept.setId(deptId);
				dept.setName("Software Product Engineering");
			}else {
				dept.setId(2L);
				dept.setName("Accountant");
			}
			return dept;
		}
		
}
