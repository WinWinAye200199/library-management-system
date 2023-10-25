package com.nexcodemm.lms.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookWithCopiedBookInfoResponse {

	//private BookResponse bookResponse;
	private List<CopiedBookResponse> copiedBooks;
	private ApiResponse apiResponse;
}
