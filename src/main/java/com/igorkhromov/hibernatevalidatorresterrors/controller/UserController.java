package com.igorkhromov.hibernatevalidatorresterrors.controller;

import com.igorkhromov.hibernatevalidatorresterrors.model.dto.SignInEmailDto;
import com.igorkhromov.hibernatevalidatorresterrors.model.dto.SignInSMSDto;
import com.igorkhromov.hibernatevalidatorresterrors.model.dto.UserProfileDto;
import com.igorkhromov.hibernatevalidatorresterrors.service.UserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user/email")
	public UserProfileDto loginEmailUser(@Valid @RequestBody SignInEmailDto signInEmailDto) {
		return userService.loginUser(signInEmailDto);
	}

	@PostMapping("/user/sms")
	public UserProfileDto loginSMSUser(@Valid SignInSMSDto signInSMSDto) {
		return userService.loginUser(signInSMSDto);
	}

	@GetMapping("/user/{id}/email")
	public UserProfileDto getUserProfile(@Min(1) @PathVariable(name = "id") Long userId) {
		return userService.getUserProfile(userId);
	}

	@GetMapping("/users")
	public UserProfileDto findUsers(@RequestParam(name="first_name") @Length(min=3) String firstName) {
		return userService.findUsers(firstName);
	}

	@GetMapping("/editors")
	public UserProfileDto findEditors(@RequestParam(name="min_age") Integer minAge) {
		return userService.findEditors(minAge);
	}

	@GetMapping("/exception")
	public UserProfileDto throwException() {
		throw new RuntimeException("I'm an uncaught exception");
	}
}