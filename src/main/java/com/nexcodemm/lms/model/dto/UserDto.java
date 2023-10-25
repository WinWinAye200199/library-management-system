package com.nexcodemm.lms.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	private long id;
	private String username;
	private String password;
	private List<Long> roleIds;
	private List<RoleDto> roles;

}
