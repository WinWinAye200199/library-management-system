package com.nexcodemm.lms.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	
	private long id;
	private String name;
	private String phone;
	private String address;
	private int totalIssued;
	private List<IssuedBookDto> issuedBookDtoLists;

}
