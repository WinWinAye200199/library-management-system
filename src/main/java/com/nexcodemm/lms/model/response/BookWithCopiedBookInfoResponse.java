package com.nexcodemm.lms.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookWithCopiedBookInfoResponse {

	private List<CopiedBookResponse> copiedBooks;

}
