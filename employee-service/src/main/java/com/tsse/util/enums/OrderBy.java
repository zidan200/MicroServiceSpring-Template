package com.tsse.util.enums;

public enum OrderBy {
	ID("empId"), FIRSTNAME("firstName");
	private String OrderByCode;

	private OrderBy(String orderBy) {
		this.OrderByCode = orderBy;
	}

	public String getOrderByCode() {
		return this.OrderByCode;
	}
}