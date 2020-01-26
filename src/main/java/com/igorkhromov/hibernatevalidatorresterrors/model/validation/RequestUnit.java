package com.igorkhromov.hibernatevalidatorresterrors.model.validation;

import lombok.*;

@Getter @Setter
@EqualsAndHashCode
public class RequestUnit {

	private RequestUnitType type;

	private String name;

	public RequestUnit(RequestUnitType type, String name) {
		this.type = type;
		this.name = name;
	}
}
