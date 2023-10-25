package com.nexcodemm.lms.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

	private Long id;
	private String username;
	private List<RoleResponse> roles;
}
