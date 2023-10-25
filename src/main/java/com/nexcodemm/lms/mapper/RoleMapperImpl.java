package com.nexcodemm.lms.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nexcodemm.lms.model.dto.RoleDto;
import com.nexcodemm.lms.model.entities.Role;
import com.nexcodemm.lms.model.response.RoleResponse;

@Component
public class RoleMapperImpl implements RoleMapper{

	@Override
	public RoleDto map(Role role) {
		if(role == null) {
			return null;
		}
		
		RoleDto roleDto = new RoleDto();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		return roleDto;
	}

	@Override
	public List<RoleDto> map(List<Role> roles) {
		if(roles == null) {
			return null;
		}
		
		List<RoleDto> roleDtos =new ArrayList<>();
		for(Role role:roles) {
			RoleDto roleDto = new RoleDto();
			roleDto.setId(role.getId());
			roleDto.setName(role.getName());
			roleDtos.add(roleDto);
		}
		return roleDtos;
	}

	@Override
	public List<RoleResponse> toResponse(List<RoleDto> roleDtos) {
		if(roleDtos == null) {
			return null;
		}
		
		List<RoleResponse> responses = new ArrayList<>();
		
		for(RoleDto dto:roleDtos) {
			RoleResponse response = new RoleResponse();
			response.setId(dto.getId());
			response.setName(dto.getName());
			
			responses.add(response);
		}
		return responses;
	}

	@Override
	public RoleResponse toResponse(RoleDto roleDto) {
		if(roleDto == null) {
			return null;
		}
		
		RoleResponse response = new RoleResponse();
		response.setId(roleDto.getId());
		response.setName(roleDto.getName());
			
		return response;
	}

}
