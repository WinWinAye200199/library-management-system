package com.nexcodemm.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexcodemm.lms.mapper.UserMapper;
import com.nexcodemm.lms.model.dto.UserDto;
import com.nexcodemm.lms.model.request.ChangePasswordRequest;
import com.nexcodemm.lms.model.request.LoginRequest;
import com.nexcodemm.lms.model.request.UserRequest;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.JwtResponse;
import com.nexcodemm.lms.security.CurrentUser;
import com.nexcodemm.lms.security.UserPrincipal;
import com.nexcodemm.lms.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	private final UserMapper userMapper;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

		
			JwtResponse jwtResponse = authService.authenticate(loginRequest);

			return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		
	}

//	@PostMapping("/signup")
//	public UserResponse signUp(@RequestBody UserRequest userRequest) {
//		UserResponse savedUser = userService.createUser(userRequest);
//		
//		return savedUser;
//	}

	@PutMapping("/reset-password")
	public ApiResponse resetPassword(@RequestBody UserRequest request) {
		UserDto userDto = userMapper.mapToDto(request);
		ApiResponse response = authService.resetPassword(userDto);

		return response;
	}

	@PutMapping("/change-password")
	public ApiResponse changedPassword(@CurrentUser UserPrincipal currentUser,
			@RequestBody ChangePasswordRequest request) {

		String oldPassword = request.getOldPassword();
		String newPassword = request.getNewPassword();
		ApiResponse response = authService.changePassword(currentUser, oldPassword, newPassword);
		return response;
	}

}
