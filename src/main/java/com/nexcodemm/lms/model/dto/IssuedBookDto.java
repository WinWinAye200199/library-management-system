package com.nexcodemm.lms.model.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssuedBookDto {

	private long id;
	private int extensionTimes;
	private LocalDate issuedDate;
	private LocalDate dueDate;
	private boolean checked;
	private boolean issued;
	private CopiedBookDto copiedBookDto;
	private String title;
	private MemberDto memberDto;
	private long memberId;
	private String generatedId;
	
	
}
