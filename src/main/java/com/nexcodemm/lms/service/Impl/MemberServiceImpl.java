package com.nexcodemm.lms.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nexcodemm.lms.mapper.MemberMapper;
import com.nexcodemm.lms.model.dto.MemberDto;
import com.nexcodemm.lms.model.entities.IssuedBook;
import com.nexcodemm.lms.model.entities.Member;
import com.nexcodemm.lms.model.excepiton.BadRequestException;
import com.nexcodemm.lms.model.excepiton.NotFoundException;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.repository.IssuedBookRepository;
import com.nexcodemm.lms.repository.MemberRepository;
import com.nexcodemm.lms.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	private final MemberMapper memberMapper;

	private final IssuedBookRepository issuedBookRepository;

	public ApiResponse createMember(MemberDto memberDto) {

		if (memberDto == null) {
			throw new BadRequestException("Please Enter Member Info!");
		}
		Member member = new Member();
		member.setName(memberDto.getName());
		member.setPhone(memberDto.getPhone());
		member.setAddress(memberDto.getAddress());
		member.setTotalIssued(0);

		memberRepository.save(member);

		return new ApiResponse(true, "Member Created Successfully!");

	}

	@Override
	public MemberDto findByPhone(String phone) {

		Member foundMember = memberRepository.findByPhoneNumber(phone);

		if (foundMember != null) {
			MemberDto memberDto = memberMapper.mapToDto(foundMember);
			return memberDto;
		} else {
			return null;
		}

	}

	@Override
	public List<MemberDto> getAllMembers() {

		List<Member> members = memberRepository.findAll();
		List<MemberDto> memberDtos = memberMapper.map(members);
		return memberDtos;
	}

	@Override
	public MemberDto findById(long id) {

		Member foundMember = memberRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Member not found with this MemberId"));
		MemberDto memberDto = memberMapper.mapToDto(foundMember);

		return memberDto;
	}

	@Override
	public ApiResponse updatedMember(long id, MemberDto memberDto) {

		List<Member> foundMembers = memberRepository.findAll(id);
		List<Member> filterLists = foundMembers.stream()
				.filter(member -> member.getPhone().equals(memberDto.getPhone())).collect(Collectors.toList());
		if (filterLists.size() == 0) {
			Member updatedMember = memberRepository.getById(id);

			updatedMember.setName(memberDto.getName());
			updatedMember.setPhone(memberDto.getPhone());
			updatedMember.setAddress(memberDto.getAddress());

			memberRepository.save(updatedMember);
			return new ApiResponse(true, "Member Updated Successfully!");
		} else {
			throw new BadRequestException("Duplicated Phone Number!");
		}
	}

	@Override
	public ApiResponse deletedMember(long id) {
		Member foundMember = memberRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Member not found with this MemberId"));
		List<IssuedBook> iBooks = issuedBookRepository.findByMemberId(foundMember.getId());
		if (foundMember.getTotalIssued() > 0 && iBooks != null) {
			throw new BadRequestException("Can not Delete!Member has Issued History.");

		} else if (foundMember.getTotalIssued() > 0) {
			throw new BadRequestException("Can not Delete!");

		} else {
			memberRepository.delete(foundMember);
			return new ApiResponse(true, "Member Deleted Successfully!");
			
		}

	}

	@Override
	public void incrementIssuedBooks(long id) {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Member not found with ID: " + id));

		int currentIssuedBooks = member.getTotalIssued();
		member.setTotalIssued(currentIssuedBooks + 1);

		memberRepository.save(member);

	}

	@Override
	public void decrementIssuedBooks(long id) {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Member not found with ID: " + id));

		int currentIssuedBooks = member.getTotalIssued();
		member.setTotalIssued(currentIssuedBooks - 1);

		memberRepository.save(member);

	}

	@Override
	public List<MemberDto> getAllMembersByFilter(String status) {

		if (status.equalsIgnoreCase("All")) {
			List<Member> members = memberRepository.findAll();
			List<MemberDto> memberDtos = memberMapper.map(members);
			return memberDtos;
		} else if (status.equalsIgnoreCase("Issued")) {
			int issued = 0;
			List<Member> members = memberRepository.findByTotalIssued(issued);
			List<MemberDto> memberDtos = memberMapper.map(members);
			return memberDtos;
		}
		return null;
	}

}
