package com.igorkhromov.hibernatevalidatorresterrors.service;

import com.igorkhromov.hibernatevalidatorresterrors.model.dto.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	public UserProfileDto loginUser(SignInEmailDto signInEmailDto) {
		return new UserProfileDto();
	}

	public UserProfileDto loginUser(SignInSMSDto signInSMSDto) {
		return new UserProfileDto();
	}

	public UserProfileDto getUserProfile(Long userId) {
		return new UserProfileDto();
	}

	public UserProfileDto findUsers(String firstName) {
		return new UserProfileDto();
	}

	public UserProfileDto findEditors(Integer minAge) {
		return new UserProfileDto();
	}
}