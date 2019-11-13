package com.tsse.client;

import java.util.concurrent.CompletableFuture;

import com.tsse.model.Department;

public interface IDepartmentEnquiryStrategy {

	CompletableFuture<Department> getDepartment(long id);

}
