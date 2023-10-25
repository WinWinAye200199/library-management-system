package com.nexcodemm.lms.service;

import com.nexcodemm.lms.model.dto.UserDto;
import com.nexcodemm.lms.model.request.LoginRequest;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.JwtResponse;
import com.nexcodemm.lms.security.UserPrincipal;

public interface AuthService {

	JwtResponse authenticate(LoginRequest loginRequest);
	ApiResponse changePassword(UserPrincipal currentUser, String oldPassword, String newPassword);
	ApiResponse resetPassword(UserDto userDto);
}
