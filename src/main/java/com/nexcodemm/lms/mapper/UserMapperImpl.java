package com.nexcodemm.lms.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexcodemm.lms.model.dto.UserDto;
import com.nexcodemm.lms.model.entities.User;
import com.nexcodemm.lms.model.request.UserRequest;
import com.nexcodemm.lms.model.response.UserResponse;

@Component
public class UserMapperImpl implements UserMapper{

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public UserDto mapToDto(User user) {
		
		if(user == null) {
			return null;
		}
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setRoles(roleMapper.map(user.getRoles()));
		
		return userDto;
	}

	@Override
	public List<UserDto> mapToDto(List<User> users) {
		if(users == null) {
			return null;
		}
		
		List<UserDto> userDtos = new ArrayList<>();
		for(User user:users) {
			
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setUsername(user.getUsername());
			userDto.setRoles(roleMapper.map(user.getRoles()));
			userDtos.add(userDto);
		}
				
		return userDtos;
	}

	@Override
	public UserResponse mapToResponse(UserDto userDto) {
		if(userDto == null) {
			return null;
		}
		UserResponse userResponse = new UserResponse();
		userResponse.setId(userDto.getId());
		userResponse.setUsername(userDto.getUsername());
		userResponse.setRoles(roleMapper.toResponse(userDto.getRoles()));
		return userResponse;
	}

	@Override
	public List<UserResponse> mapToResponse(List<UserDto> userDtos) {
		if(userDtos == null) {
			return null;
		}
		
		List<UserResponse> userResponses = new ArrayList<>();
		for(UserDto userDto:userDtos) {
			
			UserResponse userResponse = new UserResponse();
			userResponse.setId(userDto.getId());
			userResponse.setUsername(userDto.getUsername());
			userResponse.setRoles(roleMapper.toResponse(userDto.getRoles()));
			userResponses.add(userResponse);
		}
				
		return userResponses;
	}

	@Override
	public UserDto mapToDto(UserRequest request) {
		UserDto userDto = new UserDto();
		userDto.setUsername(request.getUsername());
		return userDto;
	}
}
