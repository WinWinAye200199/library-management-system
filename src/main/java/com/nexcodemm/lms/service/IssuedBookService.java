package com.nexcodemm.lms.service;

import java.util.List;

import com.nexcodemm.lms.model.dto.IssuedBookDto;
import com.nexcodemm.lms.model.response.ApiResponse;

public interface IssuedBookService {

	ApiResponse createIssuedBook(List<IssuedBookDto> iBookDtos);
	ApiResponse renew(List<Long> iBookIds);
	ApiResponse returnBook(List<Long> iBookIds);
	ApiResponse setCheckedToTrue(List<Long> iBookIds);
	ApiResponse resetCheckedFlag();
	List<IssuedBookDto> findAllIssued(int year, int month, String status);
	List<IssuedBookDto> calculateOverdueBooks();
	List<IssuedBookDto> getAllIssuedBooks();
	List<IssuedBookDto> getMembersByIssued(long id);
	
}
