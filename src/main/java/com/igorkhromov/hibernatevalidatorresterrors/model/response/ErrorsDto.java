package com.igorkhromov.hibernatevalidatorresterrors.model.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter @Setter
@EqualsAndHashCode
@JsonPropertyOrder({"message", "errors"})
public class ErrorsDto {

	private String message;

	private List<Object> errors = Collections.emptyList();

	public ErrorsDto() {
		this.message = "Internal server error";
	}

	public ErrorsDto(BindException exception) {
		this.message = "HTML form validation failed";
		this.errors = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(ValidationErrorDto::new)
				.collect(toList());
	}

	public ErrorsDto(MethodArgumentTypeMismatchException exception) {
		this.message = exception.getMessage();
	}

	public ErrorsDto(MethodArgumentNotValidException exception) {
		this.message = "JSON form validation failed";
		this.errors = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(ValidationErrorDto::new)
				.collect(toList());
	}

	public ErrorsDto(ConstraintViolationException exception) {
		this.message = "Path variable validation failed";
		this.errors = exception.getConstraintViolations()
				.stream()
				.map(ValidationErrorDto::new)
				.collect(toList());
	}

	public ErrorsDto(MissingServletRequestParameterException exception) {
		this.message = "Request parameter validation failed";
		this.errors = Collections.singletonList(new ValidationErrorDto(exception));
	}
}
