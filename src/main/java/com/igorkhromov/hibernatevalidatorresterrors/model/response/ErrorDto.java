package com.igorkhromov.hibernatevalidatorresterrors.model.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import javax.validation.ConstraintViolationException;
import java.util.Date;

@Getter @Setter
@EqualsAndHashCode
@JsonPropertyOrder({"error", "timestamp"})
public class ErrorDto {

	private ErrorsDto error;

	private Date timestamp = new Date();

	public ErrorDto() {
		this.error = new ErrorsDto();
	}

	public ErrorDto(BindException exception) {
		this.error = new ErrorsDto(exception);
	}

	public ErrorDto(MethodArgumentTypeMismatchException exception) {
		this.error = new ErrorsDto(exception);
	}

	public ErrorDto(MethodArgumentNotValidException exception) {
		this.error = new ErrorsDto(exception);
	}

	public ErrorDto(ConstraintViolationException exception) {
		this.error = new ErrorsDto(exception);
	}

	public ErrorDto(MissingServletRequestParameterException exception) {
		this.error = new ErrorsDto(exception);
	}
}
