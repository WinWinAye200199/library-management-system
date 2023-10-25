package com.nexcodemm.lms.model.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssuedResponse {

	private long id;
	private String generatedId;
	private String title;
	private long memberId;
	private boolean issued;
	private String name;
	private String phone;
	private LocalDate issuedDate;
	private boolean checked;
	private LocalDate dueDate;
	private int extensionTimes;
}
