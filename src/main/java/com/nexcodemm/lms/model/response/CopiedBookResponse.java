package com.nexcodemm.lms.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopiedBookResponse {

	private String generatedId;
	private boolean damaged;
	private boolean issued;
	private String title;
}
