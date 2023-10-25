package com.nexcodemm.lms.mapper;

import java.util.List;

import com.nexcodemm.lms.model.dto.CopiedBookDto;
import com.nexcodemm.lms.model.entities.CopiedBook;
import com.nexcodemm.lms.model.response.CopiedBookResponse;

public interface CopiedBookMapper {
	
	CopiedBookResponse mapToResponse(CopiedBookDto cBookDto);
	CopiedBookDto mapToDto(CopiedBook cBook);
	List<CopiedBookDto> mapToDto(List<CopiedBook> cBook);
	List<CopiedBookResponse> mapToResponse(List<CopiedBookDto> cBookDto);


}
