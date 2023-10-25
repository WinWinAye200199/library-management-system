package com.nexcodemm.lms.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponse {

	private long id;
	private String title;
	private int totalBooks;
	private int leftoverBooks;
	private int damagedBooks;
	private int totalIssuedBooks;
}
