package com.nexcodemm.lms.service;

import java.util.List;

import com.nexcodemm.lms.model.dto.CopiedBookDto;
import com.nexcodemm.lms.model.response.ApiResponse;

public interface CopiedBookService {

	List<CopiedBookDto> findCopiedBooksByBookId(long bookId);
	List<CopiedBookDto> findAllCopiedBooks();
	List<CopiedBookDto> findAvailableBooks();
	ApiResponse updatedCopiedBook(List<String> generatedIds);
	List<CopiedBookDto> createCopiedBook(long bookId,int totalBooks);
	CopiedBookDto findByGeneratedId(String generatedId);
	void updateAvailability(CopiedBookDto copiedBookDto);
	boolean isAvailable(long copiedBookId);
	
}
