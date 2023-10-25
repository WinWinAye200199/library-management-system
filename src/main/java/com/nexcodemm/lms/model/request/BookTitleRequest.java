package com.nexcodemm.lms.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotBlank
public class BookTitleRequest {

	private long id;
	private String title;
}
