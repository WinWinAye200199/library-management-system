package com.nexcodemm.lms.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nexcodemm.lms.model.dto.MemberDto;
import com.nexcodemm.lms.model.entities.Member;
import com.nexcodemm.lms.model.request.MemberRequest;
import com.nexcodemm.lms.model.response.MemberResponse;

@Component
public class MemberMapperImpl implements MemberMapper{

	@Override
	public MemberDto mapToDto(Member member) {
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(member.getId());
		memberDto.setName(member.getName());
		memberDto.setPhone(member.getPhone());
		memberDto.setAddress(member.getAddress());
		memberDto.setTotalIssued(member.getTotalIssued());
		return memberDto;
	}

	@Override
	public MemberResponse mapToResponse(MemberDto memberDto) {
		
		MemberResponse response = new MemberResponse();
		response.setId(memberDto.getId());
		response.setName(memberDto.getName());
		response.setPhone(memberDto.getPhone());
		response.setAddress(memberDto.getAddress());
		response.setTotalIssued(memberDto.getTotalIssued());
		return response;
	}

	@Override
	public MemberDto map(MemberRequest request) {
		
		MemberDto memberDto = new MemberDto();
		memberDto.setName(request.getName());
		memberDto.setPhone(request.getPhone());
		memberDto.setAddress(request.getAddress());
		
		return memberDto;
	}

	@Override
	public List<MemberDto> map(List<Member> members) {
		
		List<MemberDto> memberDtos = new ArrayList<>();
		for(Member member : members) {
			
			MemberDto memberDto = new MemberDto();
			memberDto.setId(member.getId());
			memberDto.setName(member.getName());
			memberDto.setPhone(member.getPhone());
			memberDto.setAddress(member.getAddress());
			memberDto.setTotalIssued(member.getTotalIssued());
			memberDtos.add(memberDto);
			
		}
		return memberDtos;
	}

	@Override
	public List<MemberResponse> maptoResponse(List<MemberDto> memberDtos) {
		
		List<MemberResponse> responses = new ArrayList<>();
		for(MemberDto memberDto : memberDtos) {
			
			MemberResponse response = new MemberResponse();
			response.setId(memberDto.getId());
			response.setName(memberDto.getName());
			response.setPhone(memberDto.getPhone());
			response.setAddress(memberDto.getAddress());
			response.setTotalIssued(memberDto.getTotalIssued());
			responses.add(response);
		}
		return responses;
	}

}
