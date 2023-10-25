package com.nexcodemm.lms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexcodemm.lms.mapper.MemberMapper;
import com.nexcodemm.lms.model.dto.MemberDto;
import com.nexcodemm.lms.model.request.MemberRequest;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.MemberResponse;
import com.nexcodemm.lms.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	private final MemberMapper memberMapper;

	@PostMapping
	public ResponseEntity<?> createMember(@RequestBody MemberRequest request) {
		
		MemberDto foundMember = memberService.findByPhone(request.getPhone());
		if (foundMember == null) {
			MemberDto memberDto = memberMapper.map(request);
			memberService.createMember(memberDto);

			ApiResponse apiResponse = new ApiResponse(true,"Member Successfully Created!");

			return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicated Phone Number!");

	}

	@GetMapping
	public List<MemberResponse> getAllMembers() {

		List<MemberDto> memberDtos = memberService.getAllMembers();
		List<MemberResponse> responses = memberMapper.maptoResponse(memberDtos);

		return responses;

	}
	
	@GetMapping("/filter")
	public List<MemberResponse> getAllByFilter(@RequestParam("status") String status){
		
		List<MemberDto> memberDtos = memberService.getAllMembersByFilter(status);
		List<MemberResponse> responses = memberMapper.maptoResponse(memberDtos);

		return responses;
	}

	@GetMapping("/searchByPhone/{phone}")
	public MemberResponse searchMember(@PathVariable("phone") String phone) {

		MemberDto memberDto = memberService.findByPhone(phone);
		MemberResponse response = memberMapper.mapToResponse(memberDto);

		return response;
	}

	@GetMapping("/{memberId}")
	public MemberResponse searchByMemberId(@PathVariable("memberId") long memberId) {

			MemberDto memberDto = memberService.findById(memberId);
			MemberResponse response = memberMapper.mapToResponse(memberDto);

			return response;
	}

	@PutMapping("/{memberId}")
	public ResponseEntity<?> updateMember(@PathVariable("memberId") long memberId, @RequestBody MemberRequest request) {

		MemberDto memberDto = memberMapper.map(request);
		ApiResponse response = memberService.updatedMember(memberId, memberDto);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@DeleteMapping("/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable("memberId") long memberId) {

		ApiResponse response = memberService.deletedMember(memberId);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
}
