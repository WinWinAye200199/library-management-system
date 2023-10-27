package com.nexcodemm.lms.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nexcodemm.lms.mapper.BookMapper;
import com.nexcodemm.lms.mapper.CopiedBookMapper;
import com.nexcodemm.lms.model.dto.BookDto;
import com.nexcodemm.lms.model.dto.CopiedBookDto;
import com.nexcodemm.lms.model.entities.Book;
import com.nexcodemm.lms.model.entities.CopiedBook;
import com.nexcodemm.lms.model.entities.IssuedBook;
import com.nexcodemm.lms.model.excepiton.BadRequestException;
import com.nexcodemm.lms.model.excepiton.NotFoundException;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.BookWithCopiedBookInfoResponse;
import com.nexcodemm.lms.model.response.CopiedBookResponse;
import com.nexcodemm.lms.repository.BookRepository;
import com.nexcodemm.lms.repository.CopiedBookRepository;
import com.nexcodemm.lms.repository.IssuedBookRepository;
import com.nexcodemm.lms.service.BookService;
import com.nexcodemm.lms.service.CopiedBookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	private final CopiedBookRepository copiedBookRepository;

	private final IssuedBookRepository iBookRepository;

	private final BookMapper bookMapper;

	private final CopiedBookMapper copiedBookMapper;

	private final CopiedBookService copiedBookService;

	@Override
	public List<CopiedBookDto> createBook(BookDto bookDto) {

		Book book = new Book();
		book.setTitle(bookDto.getTitle());
		int totalbooks = bookDto.getTotalBooks();
		book.setTotalBooks(totalbooks);
		book.setLeftoverBooks(bookDto.getTotalBooks());
		book.setDamagedBooks(0);
		book.setTotalIssuedBooks(0);

		Book savedBook = bookRepository.save(book);
		BookDto bookSaved = bookMapper.map(savedBook);

		List<CopiedBookDto> copiedBookDto = copiedBookService.createCopiedBook(bookSaved.getId(), totalbooks);

//		BookWithCopiedBookInfoResponse response = new BookWithCopiedBookInfoResponse();
//
//		response.setCopiedBooks(copiedBookMapper.mapToResponse(copiedBookDto));
//		

		return copiedBookDto;

	}

	public List<BookDto> findByTitle(String title) {

		List<Book> books = bookRepository.findByTitle(title);
		if (books == null) {
			throw new NotFoundException("Book does not exist with this Title!");
		}
		List<BookDto> bookDtos = bookMapper.maptoDto(books);

		return bookDtos;
	}

	@Override
	public List<BookDto> findAllBooks() {
		List<Book> books = bookRepository.findAll();
		List<BookDto> bookDtos = new ArrayList<>();
		for (Book book : books) {
			BookDto bookDto = bookMapper.map(book);
			bookDtos.add(bookDto);
		}
		return bookDtos;

	}

	@Override
	public BookWithCopiedBookInfoResponse updatedBook(long id, int totalBooks) {

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book does not exist with this bookId :" + id));
		List<CopiedBookResponse> copiedBooksInfo = new ArrayList<>();

		if (totalBooks > book.getDamagedBooks() || totalBooks == book.getDamagedBooks()) {
			int totalAmount = book.getTotalBooks() + totalBooks;

			int leftoverBook = book.getLeftoverBooks() + totalBooks;

			List<CopiedBook> copiedBooks = copiedBookRepository.findByBookId(id);

			int count = 1;
			int totalChanged = 0;
			for (CopiedBook copiedBook : copiedBooks) {

				if (copiedBook.isDamaged() == true) {

					copiedBook.setDamaged(false);
					copiedBookRepository.save(copiedBook);
					totalChanged++;
				}
				count++;

			}

			book.setTotalBooks(totalAmount - totalChanged);
			book.setLeftoverBooks(leftoverBook);
			book.setDamagedBooks(book.getDamagedBooks() - totalChanged);
			if (totalChanged != 0) {

				int totalUpdated = totalBooks - totalChanged;
				for (int i = 1; i <= totalUpdated; i++) {

					String generatedCopiedId = id + "B" + count + "C";

					CopiedBook cBook = new CopiedBook();
					cBook.setGeneratedId(generatedCopiedId);
					cBook.setDamaged(false);
					cBook.setBook(book);

					CopiedBook savedCopiedBook = copiedBookRepository.save(cBook);

					CopiedBookDto cBookDto = copiedBookMapper.mapToDto(savedCopiedBook);

					CopiedBookResponse copiedBookResponse = copiedBookMapper.mapToResponse(cBookDto);
					copiedBooksInfo.add(copiedBookResponse);
					count++;

				}
				bookRepository.save(book);
			} else {
				for (int i = 1; i <= totalBooks; i++) {

					String generatedCopiedId = id + "B" + count + "C";

					CopiedBook cBook = new CopiedBook();
					cBook.setGeneratedId(generatedCopiedId);
					cBook.setDamaged(false);
					cBook.setBook(book);

					CopiedBook savedCopiedBook = copiedBookRepository.save(cBook);

					CopiedBookDto cBookDto = copiedBookMapper.mapToDto(savedCopiedBook);

					CopiedBookResponse copiedBookResponse = copiedBookMapper.mapToResponse(cBookDto);
					copiedBooksInfo.add(copiedBookResponse);
					count++;

				}
			}
		} else if (totalBooks < book.getDamagedBooks()) {

			int leftoverBook = book.getLeftoverBooks() + totalBooks;
			List<CopiedBook> copiedBooks = copiedBookRepository.findByBookId(id);

			int changedCount = 0;

			for (CopiedBook copiedBook : copiedBooks) {

				if (changedCount < totalBooks && copiedBook.isDamaged()) {
					copiedBook.setDamaged(false);
					copiedBookRepository.save(copiedBook);
					changedCount++; // Increment the count of changes made
				}

			}
			book.setLeftoverBooks(leftoverBook);
			book.setDamagedBooks(book.getDamagedBooks() - totalBooks);
			bookRepository.save(book);
		}

		BookDto bookDto = bookMapper.map(book);
		bookMapper.map(bookDto);

		BookWithCopiedBookInfoResponse response = new BookWithCopiedBookInfoResponse();
		response.setCopiedBooks(copiedBooksInfo);

		return response;
	}
//
//	@Override
//	public ApiResponse deletedBook(long id) {
//		Book book = bookRepository.findById(id)
//				.orElseThrow(() -> new NotFoundException("Book does not exist with this bookId :" + id));
//		
//		System.out.println("IBook" + book.getId());
//		int totalBooks = book.getTotalBooks();
//		int damagedBooks = book.getDamagedBooks();
//		int leftoverBooks = book.getLeftoverBooks();
//		if (totalBooks == damagedBooks || totalBooks == leftoverBooks) {
//			List<CopiedBook> cBooks = copiedBookRepository.findByBookId(book.getId());
//
//			int count = 0;
//			for (CopiedBook cBook : cBooks) {
//				System.out.println("IBook" + cBook.getId());
//				IssuedBook iBook = iBookRepository.findByCopiedBookId(cBook.getId());
//				if (iBook != null) {
//					count++;
//				}
//			}
//			System.out.println("count"+count);
//			if (count == 0) {
//				bookRepository.delete(book);
//				return new ApiResponse(true, "Successfully Book Deleted!");
//			} else {
//				throw new BadRequestException("Fail to Delete! Book has Issued Lists History.");
//			}
//
//		} else {
//			return new ApiResponse(false, "Can not Delete. All Books are in good condition!");
//		}
//	}

	@Override
	public ApiResponse deletedBook(long id) {
		try {
			Book book = bookRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("Book does not exist with this bookId: " + id));

			if (book.getTotalBooks() == book.getDamagedBooks() || book.getTotalBooks() == book.getLeftoverBooks()) {
				List<CopiedBook> cBooks = copiedBookRepository.findByBookId(book.getId());

				boolean hasIssuedBooks = false;

				for (CopiedBook cBook : cBooks) {
					System.out.println("Copied Book ID: " + cBook.getId());
					IssuedBook iBook = iBookRepository.findByCopiedBookId(cBook.getId());
					if (iBook != null) {
						hasIssuedBooks = true;
						break; // No need to continue checking if an issued book is found
					}
				}

				if (!hasIssuedBooks) {
					bookRepository.delete(book);
					return new ApiResponse(true, "Book Deleted Successfully!");
				} else {
					throw new BadRequestException("Fail to Delete! Book has Issued Lists History.");
				}
			} else {
				throw new BadRequestException("Fail to Delete. Book is issued by Members!");
			}
		} catch (Exception e) {

			throw new BadRequestException("Fail to Delete!Book has Issued Lists History.");
		}
	}

	@Override
	public ApiResponse updatedBookTitle(long id, String title) {

		List<Book> books = bookRepository.findAll(id);
		List<Book> filterLists = books.stream().filter(b -> b.getTitle().equals(title)).collect(Collectors.toList());
		if (filterLists.size() > 0) {
			throw new BadRequestException("Duplicated Book Title!");
		}

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book does not exist with this bookId :" + id));
		book.setTitle(title);
		bookRepository.save(book);

		return new ApiResponse(true, "Book Title Updated Successfully!");
	}

	@Override
	public void incrementTotalIssuedBook(long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book does not exist with this bookId :" + id));
		int total = book.getTotalIssuedBooks();
		book.setTotalIssuedBooks(total + 1);

		bookRepository.save(book);

	}

	@Override
	public BookDto findById(long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book does not exist with this bookId :" + id));

		BookDto bookDto = bookMapper.map(book);

		return bookDto;
	}

	@Override
	public void decrementLeftoverBook(long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book does not exist with this bookId :" + id));

		int leftover = book.getLeftoverBooks();
		book.setLeftoverBooks(leftover - 1);

		bookRepository.save(book);

	}

	@Override
	public void returnBook(long id) {

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book does not exist with this bookId :" + id));

		book.setLeftoverBooks(book.getLeftoverBooks() + 1);
		book.setTotalIssuedBooks(book.getTotalIssuedBooks() - 1);
		bookRepository.save(book);

	}

}
