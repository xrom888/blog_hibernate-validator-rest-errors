package com.igorkhromov.hibernatevalidatorresterrors.global;

import com.igorkhromov.hibernatevalidatorresterrors.model.response.DataDto;
import com.igorkhromov.hibernatevalidatorresterrors.model.response.ErrorDto;
import com.igorkhromov.hibernatevalidatorresterrors.model.response.ErrorsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractMappingJacksonResponseBodyAdvice {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
		if (!(bodyContainer.getValue() instanceof ErrorDto)) {
			bodyContainer.setValue(new DataDto(bodyContainer.getValue()));
		}
	}

	//400 -> Invalid @RequestParam: Value conversion; Invalid @PathVariable: Value conversion
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorDto handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
		return new ErrorDto(exception);
	}

	//400 -> Invalid @RequestBody: HTML form field constraint violation
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorDto handleHtmlFormNotValid(BindException exception) {
		return new ErrorDto(exception);
	}

	//400 -> Invalid @RequestBody: JSON form field constraint violation
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorDto handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		return new ErrorDto(exception);
	}

	//400 -> Invalid @PathVariable: Constraint violation; Invalid @RequestParam: Constraint violation
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorDto handleConstraintViolationException(ConstraintViolationException exception) {
		return new ErrorDto(exception);
	}

	//400 -> Required @RequestParam missing
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorDto handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
		return new ErrorDto(exception);
	}

	//500 -> Any Throwable, that not handled
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ErrorDto handleUnhandledException(Exception ex) {
		log.error("Unhandled exception caught", ex);
		return new ErrorDto();
	}
}