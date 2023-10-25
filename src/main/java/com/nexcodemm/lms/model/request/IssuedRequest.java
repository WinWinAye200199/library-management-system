package com.nexcodemm.lms.model.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotBlank
public class IssuedRequest {

	private List<String> generatedIds;
	private long memberId;
	private String issuedDate;
	
}
