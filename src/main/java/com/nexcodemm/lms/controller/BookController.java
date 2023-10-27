package com.nexcodemm.lms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexcodemm.lms.mapper.BookMapper;
import com.nexcodemm.lms.mapper.CopiedBookMapper;
import com.nexcodemm.lms.model.dto.BookDto;
import com.nexcodemm.lms.model.dto.CopiedBookDto;
import com.nexcodemm.lms.model.excepiton.BadRequestException;
import com.nexcodemm.lms.model.request.BookRequest;
import com.nexcodemm.lms.model.request.BookTitleRequest;
import com.nexcodemm.lms.model.request.ChangedAmountRequest;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.BookResponse;
import com.nexcodemm.lms.model.response.BookWithCopiedBookInfoResponse;
import com.nexcodemm.lms.model.response.CopiedBookResponse;
import com.nexcodemm.lms.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	private final BookMapper bookMapper;
	
	private final CopiedBookMapper copiedBookMapper;

	@PostMapping
	public ResponseEntity<?> createBook(@RequestBody BookRequest request) {

		String title = request.getTitle();
		List<BookDto> books = bookService.findByTitle(title);
		if (books.size() > 0) {

			throw new BadRequestException("Duplicated Book Title!");
		}

		BookDto bookDto = new BookDto();
		bookDto.setTitle(request.getTitle());
		bookDto.setTotalBooks(request.getTotalBooks());
		List<CopiedBookDto> copiedBookDto = bookService.createBook(bookDto);
		List<CopiedBookResponse> response = copiedBookMapper.mapToResponse(copiedBookDto);

		return ResponseEntity.ok(response);

	}

	@GetMapping
	public List<BookResponse> getAllBooks() {
		List<BookDto> bookDtos = bookService.findAllBooks();
		List<BookResponse> responses = new ArrayList<>();
		for (BookDto bookDto : bookDtos) {
			BookResponse response = bookMapper.map(bookDto);
			responses.add(response);

		}
		return responses;
	}

	@PutMapping("/book-amount/{id}")
	public BookWithCopiedBookInfoResponse updatedBookAmount(@PathVariable("id") long id, @RequestBody ChangedAmountRequest request) {
		try {
			int totalBooks = request.getTotalBooks();
			BookWithCopiedBookInfoResponse response = bookService.updatedBook(id, totalBooks);
			return response;
		} catch (BadRequestException e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	@PutMapping("/book-title/{id}")
	public ApiResponse updatedBookTitle(@PathVariable("id")long id, @RequestBody BookTitleRequest request) {
		String title = request.getTitle();

		ApiResponse response = bookService.updatedBookTitle(id, title);

		return response;
	}

	@DeleteMapping("/{bookId}")
	public ApiResponse deletedBook(@PathVariable("bookId") Long bookId) {

		ApiResponse response = bookService.deletedBook(bookId);

		return response;
	}

}
