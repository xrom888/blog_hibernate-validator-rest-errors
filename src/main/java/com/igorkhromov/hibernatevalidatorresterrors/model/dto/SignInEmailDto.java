package com.igorkhromov.hibernatevalidatorresterrors.model.dto;

import lombok.*;
import javax.validation.constraints.*;

@Getter @Setter
@EqualsAndHashCode
public class SignInEmailDto {

	@NotEmpty @Email
	private String email;

	@Pattern(regexp="^[a-zA-Z0-9]{6,12}$")
	private String password;
}