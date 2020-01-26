package com.igorkhromov.hibernatevalidatorresterrors.model.response;

import com.igorkhromov.hibernatevalidatorresterrors.model.validation.RequestUnit;
import com.igorkhromov.hibernatevalidatorresterrors.model.validation.RequestUnitType;
import lombok.*;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.ConstraintViolation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@EqualsAndHashCode
public class ValidationErrorDto {

	private static final Logger log = LoggerFactory.getLogger(ValidationErrorDto.class);

	private String unit;

	private String name;

	private String message;

	private Object rejectedValue;

	public ValidationErrorDto(FieldError error) {
		this.unit = RequestUnitType.FIELD.getName();
		this.name = error.getField();
		this.message = error.getDefaultMessage();
		this.rejectedValue = error.getRejectedValue();
	}

	public ValidationErrorDto(ConstraintViolation<?> constraint) {
		RequestUnit requestUnit = getConstraintUnit(constraint);
		this.unit = requestUnit.getType().getName();
		this.name = requestUnit.getName();
		this.message = constraint.getMessage();
		this.rejectedValue = constraint.getInvalidValue();
	}

	public ValidationErrorDto(MissingServletRequestParameterException exception) {
		this.unit = RequestUnitType.REQUEST_PARAM.getName();
		this.name = exception.getParameterName();
		this.message = exception.getMessage();
	}

	private RequestUnit getConstraintUnit(ConstraintViolation<?> c) {
		PathImpl path = (PathImpl) c.getPropertyPath();
		NodeImpl currentNode = path.getLeafNode();

		RequestUnitType unitType = RequestUnitType.FIELD;
		String unitName = currentNode.getName();

		try {
			List<Method> methods = Arrays.asList(c.getRootBean().getClass().getDeclaredMethods());
			Optional<Method> optMethod = methods.stream()
					.filter(m -> m.getName().equals(currentNode.getParent().getName()))
					.findFirst();

			if (optMethod.isPresent()) {
				Parameter[] params = optMethod.get().getParameters();
				unitName = params[currentNode.getParameterIndex()].getName();

				PathVariable pathVariable = params[currentNode.getParameterIndex()]
						.getAnnotation(PathVariable.class);
				if (null != pathVariable) {
					unitType = RequestUnitType.PATH_VARIABLE;
					unitName = getUnitName(pathVariable, unitName);
				} else {
					unitType = RequestUnitType.REQUEST_PARAM;
					RequestParam requestParam = params[currentNode.getParameterIndex()]
							.getAnnotation(RequestParam.class);
					unitName = getUnitName(requestParam, unitName);
				}
			}
		} catch (Exception e) {
			log.error("Cannot process ConstraintViolation object", e);
		}

		return new RequestUnit(unitType, unitName);
	}

	private String getUnitName(RequestParam requestParam, String defaultValue) {
		if (null == requestParam) return defaultValue;
		if (!requestParam.name().isEmpty()) {
			return requestParam.name();
		} else if (!requestParam.value().isEmpty()) {
			return requestParam.value();
		}
		return defaultValue;
	}

	private String getUnitName(PathVariable pathVariable, String defaultValue) {
		if (null == pathVariable) return defaultValue;
		if (!pathVariable.name().isEmpty()) {
			return pathVariable.name();
		}
		else if (!pathVariable.value().isEmpty()) {
			return pathVariable.value();
		}
		return defaultValue;
	}
}