package com.nexcodemm.lms.mapper;

import java.util.List;

import com.nexcodemm.lms.model.dto.RoleDto;
import com.nexcodemm.lms.model.entities.Role;
import com.nexcodemm.lms.model.response.RoleResponse;

public interface RoleMapper {

	RoleDto map(Role role);
	List<RoleDto> map(List<Role> roles);
	List<RoleResponse>toResponse(List<RoleDto>roleDtos);
	RoleResponse toResponse(RoleDto roleDto);
}
