package com.nexcodemm.lms.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotBlank
public class UserRequest {

	private String username;
	//private String password;
	//private List<Long> roleIds;
	
}
