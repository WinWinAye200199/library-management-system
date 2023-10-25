package com.nexcodemm.lms.mapper;

import java.util.List;

import com.nexcodemm.lms.model.dto.MemberDto;
import com.nexcodemm.lms.model.entities.Member;
import com.nexcodemm.lms.model.request.MemberRequest;
import com.nexcodemm.lms.model.response.MemberResponse;

public interface MemberMapper {

	MemberDto mapToDto(Member member);
	MemberResponse mapToResponse(MemberDto memberDto);
	MemberDto map(MemberRequest request);
	List<MemberDto> map(List<Member> members);
	List<MemberResponse> maptoResponse(List<MemberDto> memberDtos);
}
