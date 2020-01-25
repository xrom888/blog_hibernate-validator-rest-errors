package com.igorkhromov.hibernatevalidatorresterrors.model.dto;

import lombok.*;

@Getter @Setter
@EqualsAndHashCode
public class UserProfileDto {

	private String firstName;

	private String email;

	private String birthDate;

	private String friends;
}