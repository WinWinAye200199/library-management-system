package com.nexcodemm.lms.service.Impl;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nexcodemm.lms.model.dto.UserDto;
import com.nexcodemm.lms.model.entities.User;
import com.nexcodemm.lms.model.excepiton.BadRequestException;
import com.nexcodemm.lms.model.excepiton.NotFoundException;
import com.nexcodemm.lms.model.excepiton.UnauthorizedException;
import com.nexcodemm.lms.model.request.LoginRequest;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.JwtResponse;
import com.nexcodemm.lms.repository.UserRepository;
import com.nexcodemm.lms.security.UserPrincipal;
import com.nexcodemm.lms.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JwtServiceImpl jwtService;

	@Override
	public JwtResponse authenticate(LoginRequest loginRequest) {
		try {
			Date expiredAt = new Date((new Date()).getTime() + 86400 * 1000);

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				String jwtToken = jwtService.createToken(authentication);
				return new JwtResponse("Bearer", jwtToken, expiredAt.toInstant().toString());
			} else {
				throw new UnauthorizedException("Username or Password is wrong!");
			}
		}catch(Exception e) {
			throw new UnauthorizedException("Username or Password is wrong!");
		}
		
	}

	@Override
	public ApiResponse changePassword(UserPrincipal currentUser, String oldPassword, String newPassword) {

		User foundUser = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(
				() -> new BadRequestException("Username Not Found : username -> " + currentUser.getUsername()));

		String foundPassword = foundUser.getPassword();

		if (passwordEncoder.matches(oldPassword, foundPassword)) {

			foundUser.setPassword(passwordEncoder.encode(newPassword));

			userRepository.save(foundUser);
			return new ApiResponse(true, "Changed Password Successfully!");
		} else {

//				return new ApiResponse(false,"Wrong Old Password!");
			throw new BadRequestException("Wrong Old Password!");
		}

	}

//	@Override
//	public UserResponse createUser(UserRequest userRequest) {
//
//		User user = new User();
//		user.setUsername(userRequest.getUsername());
//		
//		List<Role>roles = new ArrayList<>();
//		List<Long> roleIds = userRequest.getRoleIds();
//		for (Long roleId : roleIds) {
//			Role role = roleRepository.findById(roleId).orElse(null);
//			roles.add(role);
//		}
//		user.setRoles(roles);
//		String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
//		user.setPassword(encodedPassword);
//
//		User savedUser = userRepository.save(user);
//		UserDto savedUserDto = userMapper.mapToDto(savedUser);
//		UserResponse savedUserResponse = userMapper.mapToResponse(savedUserDto);
//
//		return savedUserResponse;
//	}

	@Override
	public ApiResponse resetPassword(UserDto userDto) {

		User foundUser = userRepository.findByUsername(userDto.getUsername())
				.orElseThrow(() -> new BadRequestException("Username Not Found!"));
		if (foundUser != null) {
			String resetPassword = "Admin@123";
			foundUser.setPassword(passwordEncoder.encode(resetPassword));
			userRepository.save(foundUser);

			return new ApiResponse(true, "Reset Password Successfully!");

		}
//			return new ApiResponse(false,"Wrong Username!");
		throw new NotFoundException("Username Not Found!");
	}

}
