package com.nexcodemm.lms.mapper;

import java.util.List;

import com.nexcodemm.lms.model.dto.UserDto;
import com.nexcodemm.lms.model.entities.User;
import com.nexcodemm.lms.model.request.UserRequest;
import com.nexcodemm.lms.model.response.UserResponse;

public interface UserMapper {

	UserDto mapToDto(User user);
	List<UserDto> mapToDto(List<User> users);
	UserResponse mapToResponse(UserDto userDto);
	List<UserResponse> mapToResponse(List<UserDto> userDtos);
	UserDto mapToDto(UserRequest request);
}
