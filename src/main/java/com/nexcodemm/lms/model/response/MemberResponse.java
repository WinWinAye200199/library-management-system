package com.nexcodemm.lms.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {

	private long id;
	private String name;
	private String phone;
	private String address;
	private int totalIssued;
	//private List<IssuedBookDto> issuedBookDtoLists; 
}
