package com.tsse.factory;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.tsse.client.DepartmentInternalClientStrategy;
import com.tsse.client.DepartmentExternalClientStrategy;
import com.tsse.client.IDepartmentEnquiryStrategy;
import javax.annotation.Resource;

import lombok.Data;

@Data
@Component
public class ClientFactory {

	@Resource(name = "client1")
	private IDepartmentEnquiryStrategy departmentRestClient;
	
	@Resource(name = "client2")
	private IDepartmentEnquiryStrategy departmentRestClient2;
	
	public IDepartmentEnquiryStrategy getClient(String useCase) {
		if("client2".equals(useCase)) {
			return departmentRestClient2;
		}
		return departmentRestClient;
	}
}
