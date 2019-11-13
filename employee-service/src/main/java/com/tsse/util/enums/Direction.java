package com.tsse.util.enums;

public enum Direction {
	ASCENDING("ASC"), DESCENDING("DESC");
	private final String directionCode;

	private Direction(String direction) {
		this.directionCode = direction;
	}

	public String getDirectionCode() {
		return this.directionCode;
	}
}