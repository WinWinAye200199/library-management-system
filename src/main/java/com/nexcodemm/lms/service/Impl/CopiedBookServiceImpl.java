package com.nexcodemm.lms.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nexcodemm.lms.mapper.CopiedBookMapper;
import com.nexcodemm.lms.model.dto.CopiedBookDto;
import com.nexcodemm.lms.model.entities.Book;
import com.nexcodemm.lms.model.entities.CopiedBook;
import com.nexcodemm.lms.model.excepiton.BadRequestException;
import com.nexcodemm.lms.model.excepiton.NotFoundException;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.repository.BookRepository;
import com.nexcodemm.lms.repository.CopiedBookRepository;
import com.nexcodemm.lms.service.CopiedBookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CopiedBookServiceImpl implements CopiedBookService {

	private final CopiedBookRepository copiedBookRepository;

	private final CopiedBookMapper copiedBookMapper;

	private final BookRepository bookRepository;

	@Override
	public List<CopiedBookDto> findCopiedBooksByBookId(long bookId) {
		List<CopiedBook> cBooks = copiedBookRepository.findByBookId(bookId);
		List<CopiedBookDto> cBookDtos = copiedBookMapper.mapToDto(cBooks);
		return cBookDtos;
	}

	@Override
	public List<CopiedBookDto> findAllCopiedBooks() {
		List<CopiedBook> cBooks = copiedBookRepository.findAll();

		List<CopiedBookDto> cBookDtos = copiedBookMapper.mapToDto(cBooks);

		return cBookDtos;
	}

	@Override
	public List<CopiedBookDto> findAvailableBooks() {

		List<CopiedBook> cBooks = copiedBookRepository.findByIssued(false);
		List<CopiedBookDto> cBookDtos = copiedBookMapper.mapToDto(cBooks);

		return cBookDtos;
	}

	@Override
	public ApiResponse updatedCopiedBook(List<String> generatedIds) {

		ApiResponse response = new ApiResponse();
//		List<ApiResponse> responses = new ArrayList<>();
		List<String> lists = new ArrayList<>();
		for (String generatedId : generatedIds) {

			CopiedBook cBook = copiedBookRepository.findByGeneratedId(generatedId);

			if (cBook.isIssued() == true) {
				lists.add(cBook.getGeneratedId());
			}

		}
		if (lists.size() > 1) {

			throw new BadRequestException(lists.toString() + " are issued!");
		} else if (lists.size() == 1) {
			throw new BadRequestException(lists.toString() + " is issued!");
		}
		for (String generatedId : generatedIds) {
			CopiedBook cBook = copiedBookRepository.findByGeneratedId(generatedId);

			if (cBook.isDamaged() == true && cBook.isIssued() == true) {

				throw new BadRequestException("Some Books are in issued!");

			} else {

				cBook.setDamaged(true);
				Book book = bookRepository.findById(cBook.getBook().getId())
						.orElseThrow(() -> new NotFoundException("Book does not exist with this ID"));
				book.setDamagedBooks(cBook.getBook().getDamagedBooks() + 1);
				book.setLeftoverBooks(cBook.getBook().getLeftoverBooks() - 1);
				copiedBookRepository.save(cBook);
				bookRepository.save(book);
				response.setMessage("Books added to Damaged List Successfully!");
				response.setSuccess(true);

			}

		}

		return response;
	}

	@Override
	public List<CopiedBookDto> createCopiedBook(long bookId, int totalBooks) {

		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new NotFoundException("Book does not exist with this ID"));
		List<CopiedBookDto> copiedBooksInfo = new ArrayList<>();
		for (int i = 1; i <= totalBooks; i++) {

			String generatedCopiedId = bookId + "B" + i + "C";
			CopiedBook cBook = new CopiedBook();
			cBook.setGeneratedId(generatedCopiedId);
			cBook.setDamaged(false);
			cBook.setBook(book);
			CopiedBook savedCopiedBook = copiedBookRepository.save(cBook);
			CopiedBookDto copiedBookDto = copiedBookMapper.mapToDto(savedCopiedBook);

			copiedBooksInfo.add(copiedBookDto);

		}

		return copiedBooksInfo;
	}

	@Override
	public CopiedBookDto findByGeneratedId(String generatedId) {

		CopiedBook cBook = copiedBookRepository.findByGeneratedId(generatedId);
		CopiedBookDto cBookDto = copiedBookMapper.mapToDto(cBook);

		return cBookDto;
	}

	@Override
	public void updateAvailability(CopiedBookDto copiedBookDto) {

		String copiedBookId = copiedBookDto.getGeneratedId();

		CopiedBook copiedBook = copiedBookRepository.findByGeneratedId(copiedBookId);

		copiedBook.setIssued(true);

		copiedBookRepository.save(copiedBook);

	}

	@Override
	public boolean isAvailable(long copiedBookId) {
		CopiedBook copiedBook = copiedBookRepository.findById(copiedBookId)
				.orElseThrow(() -> new NotFoundException("CopiedBook not found with ID: " + copiedBookId));

		return !copiedBook.isIssued();
	}

}
