package com.nexcodemm.lms.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopiedBookWithTitleResponse {

	private long generatedId;
	private boolean damaged;
	private String title;
}
