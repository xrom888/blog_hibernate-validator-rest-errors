package com.igorkhromov.hibernatevalidatorresterrors.model.validation;

public enum RequestUnitType {

	FIELD("field"),
	PATH_VARIABLE("pathVariable"),
	REQUEST_PARAM("requestParam");

	private String name;

	RequestUnitType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}