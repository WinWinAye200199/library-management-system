package com.nexcodemm.lms.service;

import java.util.List;

import com.nexcodemm.lms.model.dto.MemberDto;
import com.nexcodemm.lms.model.response.ApiResponse;

public interface MemberService {

	ApiResponse createMember(MemberDto memberDto);
	MemberDto findByPhone(String phone);
	List<MemberDto> getAllMembers();
	List<MemberDto> getAllMembersByFilter(String status);
	MemberDto findById(long id);
	ApiResponse updatedMember(long id, MemberDto memberDto);
	ApiResponse deletedMember(long id);
	void incrementIssuedBooks(long id);
	void decrementIssuedBooks(long id);
	
}
