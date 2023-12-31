package com.nexcodemm.lms.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {

	private long id;
	private String name;
	private List<UserDto> users;
}
