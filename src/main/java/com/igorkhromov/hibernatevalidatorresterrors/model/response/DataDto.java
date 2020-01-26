package com.igorkhromov.hibernatevalidatorresterrors.model.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import java.util.Date;

@Getter @Setter
@EqualsAndHashCode
@JsonPropertyOrder({"data", "timestamp"})
public class DataDto {

	private Object data;

	public DataDto(Object data) {
		this.data = data;
	}

	private Date timestamp = new Date();
}