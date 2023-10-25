package com.nexcodemm.lms.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CopiedBookDto {

	private long id;
	private String generatedId;
	private boolean isDamaged;
	private boolean isIssued;
	private BookDto bookDto;
	private long bookId;
	private List<IssuedBookDto> issuedBookLists;
	
}
