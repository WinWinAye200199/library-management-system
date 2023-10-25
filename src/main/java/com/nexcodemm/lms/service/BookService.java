package com.nexcodemm.lms.service;

import java.util.List;

import com.nexcodemm.lms.model.dto.BookDto;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.BookWithCopiedBookInfoResponse;

public interface BookService {
	
	BookWithCopiedBookInfoResponse createBook(BookDto bookDto);
	List<BookDto> findByTitle(String bookTitle);
	List<BookDto> findAllBooks();
	BookWithCopiedBookInfoResponse updatedBook(long bookId,int totalBooks);
	ApiResponse updatedBookTitle(long bookId, String bookTitle);
	ApiResponse deletedBook(long bookId);
	void incrementTotalIssuedBook(long bookId);
	BookDto findById(long bookId);
	void decrementLeftoverBook(long bookId);
	void returnBook(long bookId);

}
