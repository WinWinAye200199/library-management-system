package com.nexcodemm.lms.mapper;

import java.util.List;

import com.nexcodemm.lms.model.dto.BookDto;
import com.nexcodemm.lms.model.entities.Book;
import com.nexcodemm.lms.model.response.BookResponse;

public interface BookMapper {

	BookDto map(Book book);
	List<BookDto> maptoDto(List<Book> books);
	BookResponse map(BookDto bookDto);
	List<BookResponse> maptoResponse(List<BookDto> bookDtos);
	
}
