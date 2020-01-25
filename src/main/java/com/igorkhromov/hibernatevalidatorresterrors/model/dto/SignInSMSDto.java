package com.igorkhromov.hibernatevalidatorresterrors.model.dto;

import lombok.*;
import javax.validation.constraints.*;

@Getter @Setter
@EqualsAndHashCode
public class SignInSMSDto {

	@NotEmpty @Size(min=3)
	private String firstName;

	@NotEmpty @Pattern(regexp="^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")
	private String phoneNumber;
}
