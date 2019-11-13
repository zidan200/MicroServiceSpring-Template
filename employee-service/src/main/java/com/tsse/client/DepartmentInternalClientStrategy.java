package com.tsse.client;

import org.springframework.scheduling.annotation.Async;
import org.springframework.http.*;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsse.config.SecurityContextUtils;
import com.tsse.model.Department;
import com.tsse.service.EmployeeService;
import org.springframework.web.client.*;

import lombok.extern.slf4j.Slf4j;

@Service("client1")
@CacheConfig(cacheNames = { "departments" })
@Transactional
@Slf4j
public class DepartmentInternalClientStrategy implements IDepartmentEnquiryStrategy {

	@Async
	@Override
	public CompletableFuture<Department> getDepartment(long id) {
		RestTemplate restTemplate = new RestTemplate();
		log.info("get department - Start calling rest api - /departments/" + id);
		HttpHeaders headers = new HttpHeaders();
		// headers.set("Authorization", "Bearer " + "");
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		ResponseEntity<Department> deptResponse = restTemplate.exchange("http://localhost:8090/departments/" + id,
				HttpMethod.GET, entity, Department.class);
		log.info("get department - End calling rest api - /departments/" + id);
		Department dept = deptResponse.getBody();
		return CompletableFuture.completedFuture(dept);
	}
}