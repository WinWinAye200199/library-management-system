package com.nexcodemm.lms.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotBlank
public class BookRequest {

	private String title;
	private int totalBooks;
}
