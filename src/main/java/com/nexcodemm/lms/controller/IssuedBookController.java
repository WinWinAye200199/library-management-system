package com.nexcodemm.lms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexcodemm.lms.mapper.IssuedBookMapper;
import com.nexcodemm.lms.model.dto.IssuedBookDto;
import com.nexcodemm.lms.model.excepiton.AppException;
import com.nexcodemm.lms.model.request.IssuedRequest;
import com.nexcodemm.lms.model.request.RenewReturnRequest;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.IssuedResponse;
import com.nexcodemm.lms.service.IssuedBookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/issued-books")
public class IssuedBookController {

	private final IssuedBookService iBookService;
	private final IssuedBookMapper iBookMapper;
	@PostMapping
	public ApiResponse createIssued(@RequestBody IssuedRequest request) {

		List<IssuedBookDto> iBookDtos = iBookMapper.mapToDto(request);
		
		ApiResponse response = iBookService.createIssuedBook(iBookDtos);
		// List<IssuedResponse> responses = iBookMapper.mapToResponse(iBookDto1);

		return response;
	}

	@PutMapping("/renew")
	public ApiResponse renew(@RequestBody RenewReturnRequest request) {

		List<Long> iBookIds = request.getIds();

		ApiResponse response = iBookService.renew(iBookIds);

		return response;
	}

	@PutMapping("/return")
	public ApiResponse returnBook(@RequestBody RenewReturnRequest request) {

		List<Long> iBookIds = request.getIds();

		ApiResponse response = iBookService.returnBook(iBookIds);

		return response;
	}

	@GetMapping("/filter")
	public List<IssuedResponse> getBooksByMonthAndYear(@RequestParam("date") String dateString,
									@RequestParam("status") String status) {

		List<IssuedResponse> responses = null;
        // Split the input string using "-" as the delimiter
        String[] parts = dateString.split("-");
		 if (parts.length == 2) {
	            try {
	                int year = Integer.parseInt(parts[0]); // Parse the year component as an integer
	                int month = Integer.parseInt(parts[1]); // Parse the month component as an integer
	                List<IssuedBookDto> iBookDtos = iBookService.findAllIssued(year, month,status);
	                responses = iBookMapper.mapToResponse(iBookDtos); 
	            } catch (NumberFormatException e) {
	                throw new AppException("Invalid year or month format.");
	            }
	        } else {
	            throw new AppException("Invalid input format. Expected yyyy-MM.");
	        }
		 return responses;
	}

	@GetMapping("/overdue")
	public List<IssuedResponse> getAllOverdueBooks() {

		List<IssuedBookDto> iBookDtos = iBookService.calculateOverdueBooks();
		List<IssuedResponse> response = iBookMapper.mapToResponse(iBookDtos);
		return response;
	}
	@GetMapping
	public List<IssuedResponse> getAllIssuedBooks(){
		List<IssuedBookDto> iBookDtos = iBookService.getAllIssuedBooks();
		List<IssuedResponse> response = iBookMapper.mapToResponse(iBookDtos);
		return response;
	}
	
	@GetMapping("/issued-lists-by-member/{memberId}")
	public List<IssuedResponse> getIssuedBookByMemberId(@PathVariable("memberId")long id) {
		List<IssuedBookDto> iBookDtos = iBookService.getMembersByIssued(id);
		List<IssuedResponse> response = iBookMapper.mapToResponse(iBookDtos);
		return response;
	}
	@PutMapping("/check")
	public ApiResponse checkIssued(@RequestBody RenewReturnRequest request) {
		
		List<Long> iBookIds = request.getIds();

		ApiResponse response = iBookService.setCheckedToTrue(iBookIds);

		return response;
	}
	
}
