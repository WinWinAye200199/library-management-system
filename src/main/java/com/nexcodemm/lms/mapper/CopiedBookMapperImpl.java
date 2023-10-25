package com.nexcodemm.lms.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nexcodemm.lms.model.dto.BookDto;
import com.nexcodemm.lms.model.dto.CopiedBookDto;
import com.nexcodemm.lms.model.entities.Book;
import com.nexcodemm.lms.model.entities.CopiedBook;
import com.nexcodemm.lms.model.response.CopiedBookResponse;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Component
public class CopiedBookMapperImpl implements CopiedBookMapper {
	
	private final BookMapper bookMapper;
	
	@Override
	public CopiedBookResponse mapToResponse(CopiedBookDto cBookDto) {

		if (cBookDto == null) {
			return null;
		}

		CopiedBookResponse response = new CopiedBookResponse();
		response.setGeneratedId(cBookDto.getGeneratedId());
		response.setDamaged(cBookDto.isDamaged());
		response.setIssued(cBookDto.isIssued());
		response.setTitle(cBookDto.getBookDto().getTitle());

		return response;
	}

	@Override
	public CopiedBookDto mapToDto(CopiedBook cBook) {

		if (cBook == null) {
			return null;
		}

		CopiedBookDto cBookDto = new CopiedBookDto();
		cBookDto.setBookId(cBook.getBook().getId());
		cBookDto.setId(cBook.getId());
		cBookDto.setGeneratedId(cBook.getGeneratedId());
		cBookDto.setDamaged(cBook.isDamaged());
		Book bookEntity = cBook.getBook();
		BookDto bookDto = bookMapper.map(bookEntity);
		cBookDto.setBookDto(bookDto);
		
		return cBookDto;
	}

	@Override
	public List<CopiedBookDto> mapToDto(List<CopiedBook> cBooks) {

		if (cBooks == null) {
			return null;
		}

		List<CopiedBookDto> cBookDtos = new ArrayList<>();
		for (CopiedBook cBook : cBooks) {
			CopiedBookDto cBookDto = new CopiedBookDto();
			cBookDto.setGeneratedId(cBook.getGeneratedId());
			cBookDto.setIssued(cBook.isIssued());
			cBookDto.setDamaged(cBook.isDamaged());
			Book bookEntity = cBook.getBook();
			BookDto bookDto = bookMapper.map(bookEntity);
			cBookDto.setBookDto(bookDto);
			cBookDtos.add(cBookDto);
		}
		return cBookDtos;
	}

	@Override
	public List<CopiedBookResponse> mapToResponse(List<CopiedBookDto> cBookDtos) {

		if (cBookDtos == null) {
			return null;
		}

		List<CopiedBookResponse> responses = new ArrayList<>();
		for (CopiedBookDto cBookDto : cBookDtos) {

			CopiedBookResponse response = new CopiedBookResponse();
			response.setIssued(cBookDto.isIssued());
			response.setGeneratedId(cBookDto.getGeneratedId());
			response.setDamaged(cBookDto.isDamaged());
			response.setTitle(cBookDto.getBookDto().getTitle());

			responses.add(response);
		}
		return responses;
	}

}
