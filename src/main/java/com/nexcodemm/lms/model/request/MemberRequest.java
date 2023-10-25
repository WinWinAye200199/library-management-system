package com.nexcodemm.lms.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotBlank
public class MemberRequest {

	
	private String name;
	private String phone;
	private String address;
}
