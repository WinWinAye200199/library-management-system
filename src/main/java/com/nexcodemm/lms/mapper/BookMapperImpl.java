package com.nexcodemm.lms.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nexcodemm.lms.model.dto.BookDto;
import com.nexcodemm.lms.model.entities.Book;
import com.nexcodemm.lms.model.response.BookResponse;

@Component
public class BookMapperImpl implements BookMapper{

	@Override
	public BookDto map(Book book) {
		BookDto bookDto = new BookDto();
		bookDto.setId(book.getId());
		bookDto.setTitle(book.getTitle());
		bookDto.setDamagedBooks(book.getDamagedBooks());
		bookDto.setLeftoverBooks(book.getLeftoverBooks());
		bookDto.setTotalBooks(book.getTotalBooks());
		bookDto.setTotalIssuedBooks(book.getTotalIssuedBooks());
		return bookDto;
	}

	@Override
	public List<BookDto> maptoDto(List<Book> books) {
		List<BookDto> bookDtos = new ArrayList<>();
		for(Book book : books) {
			BookDto bookDto = new BookDto();
			bookDto = map(book);
//			bookDto.setId(book.getId());
//			bookDto.setTitle(book.getTitle());
//			bookDto.setDamagedBooks(book.getDamagedBooks());
//			bookDto.setLeftoverBooks(book.getLeftoverBooks());
//			bookDto.setTotalIssuedBooks(book.getTotalIssuedBooks());
//			bookDto.setTotalBooks(book.getTotalBooks());
			bookDtos.add(bookDto);
		}
		return bookDtos;
	}

	@Override
	public BookResponse map(BookDto bookDto) {
		BookResponse response = new BookResponse();
		response.setId(bookDto.getId());
		response.setTitle(bookDto.getTitle());
		response.setDamagedBooks(bookDto.getDamagedBooks());
		response.setLeftoverBooks(bookDto.getLeftoverBooks());
		response.setTotalBooks(bookDto.getTotalBooks());
		response.setTotalIssuedBooks(bookDto.getTotalIssuedBooks());
		return response;
	}

	@Override
	public List<BookResponse> maptoResponse(List<BookDto> bookDtos) {
		List<BookResponse> responses = new ArrayList<>();
		for(BookDto bookDto : bookDtos) {
			BookResponse response = new BookResponse();
			response = map(bookDto);
//			response.setId(bookDto.getId());
//			response.setTitle(bookDto.getTitle());
//			response.setDamagedBooks(bookDto.getDamagedBooks());
//			response.setLeftoverBooks(bookDto.getLeftoverBooks());
//			response.setTotalBooks(bookDto.getTotalBooks());
//			response.setTotalIssuedBooks(bookDto.getTotalIssuedBooks());
			responses.add(response);
		}
				
		return responses;
	}

}
