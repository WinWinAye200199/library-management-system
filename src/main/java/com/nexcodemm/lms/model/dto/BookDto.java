package com.nexcodemm.lms.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BookDto {
	
	private long id;
	private String title;
	private int totalBooks;
	private int leftoverBooks;
	private int damagedBooks;
	private int totalIssuedBooks;
	private List<CopiedBookDto> copiedBookLists;

}
