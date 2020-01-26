package com.igorkhromov.hibernatevalidatorresterrors.model.dto;

import lombok.*;

@Getter @Setter
@EqualsAndHashCode
public class UserProfileDto {

	private String firstName = "Jack";

	private String email = "jack@london.uk";

	private String birthDate = "January 12, 1876";
}