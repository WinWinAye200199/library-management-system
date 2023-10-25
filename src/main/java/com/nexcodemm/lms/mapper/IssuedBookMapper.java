package com.nexcodemm.lms.mapper;

import java.util.List;

import com.nexcodemm.lms.model.dto.IssuedBookDto;
import com.nexcodemm.lms.model.entities.IssuedBook;
import com.nexcodemm.lms.model.request.IssuedRequest;
import com.nexcodemm.lms.model.response.IssuedResponse;

public interface IssuedBookMapper {

	List<IssuedBookDto> mapToDto(IssuedRequest request);
	IssuedBookDto mapToDto(IssuedBook iBook);
	List<IssuedResponse> mapToResponse(List<IssuedBookDto> iBookDtos);
	List<IssuedBookDto> mapToDto(List<IssuedBook> iBooks);
}
