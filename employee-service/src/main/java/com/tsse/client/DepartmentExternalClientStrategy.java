package com.tsse.client;

import java.util.concurrent.CompletableFuture;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.tsse.model.Department;

import lombok.extern.slf4j.Slf4j;

@Service("client2")
@CacheConfig(cacheNames = { "departments2" })
@Transactional
@Slf4j
public class DepartmentExternalClientStrategy implements IDepartmentEnquiryStrategy {

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