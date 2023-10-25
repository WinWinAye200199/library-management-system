package com.nexcodemm.lms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexcodemm.lms.mapper.CopiedBookMapper;
import com.nexcodemm.lms.model.dto.CopiedBookDto;
import com.nexcodemm.lms.model.request.DamagedRequest;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.CopiedBookResponse;
import com.nexcodemm.lms.service.CopiedBookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/copied-books")
public class CopiedBookController {

	private final CopiedBookService copiedBookService;
	
	private final CopiedBookMapper copiedBookMapper;
	
	@GetMapping
	public List<CopiedBookResponse> getAllBooks() {
		
		List<CopiedBookDto> cBookDto = copiedBookService.findAllCopiedBooks();
		List<CopiedBookResponse> responses = copiedBookMapper.mapToResponse(cBookDto);
		
		return responses;
	}
	
	@GetMapping("/{bookId}")
	public List<CopiedBookResponse> getCopiedBooks(@PathVariable("bookId") long bookId){
		
		List<CopiedBookDto> cBookDto = copiedBookService.findCopiedBooksByBookId(bookId);
		List<CopiedBookResponse> responses = copiedBookMapper.mapToResponse(cBookDto);
		
		return responses;
	}
	@PutMapping
	public ApiResponse updatedCopiedBook(@RequestBody DamagedRequest request) {
		
		List<String> generatedIds = request.getGeneratedIds();
		ApiResponse response = copiedBookService.updatedCopiedBook(generatedIds);
		//CopiedBookResponse response = copiedBookMapper.mapToResponse(cBookDto);
		
		return response;
	}
}
