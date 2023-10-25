package com.nexcodemm.lms.mapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nexcodemm.lms.model.dto.CopiedBookDto;
import com.nexcodemm.lms.model.dto.IssuedBookDto;
import com.nexcodemm.lms.model.dto.MemberDto;
import com.nexcodemm.lms.model.entities.IssuedBook;
import com.nexcodemm.lms.model.excepiton.BadRequestException;
import com.nexcodemm.lms.model.request.IssuedRequest;
import com.nexcodemm.lms.model.response.IssuedResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class IssuedBookMapperImpl implements IssuedBookMapper {

	private final CopiedBookMapper copiedBookMapper;

	private final MemberMapper memberMapper;

	@Override
	public List<IssuedBookDto> mapToDto(IssuedRequest request) {

		List<IssuedBookDto> iBookDtos = new ArrayList<>();
        for(String generatedId : request.getGeneratedIds()) {
        	
        	IssuedBookDto iBookDto = new IssuedBookDto();
    		String issued_date = request.getIssuedDate();
        	iBookDto.setGeneratedId(generatedId);
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	    ZoneId utcZone = ZoneId.of("UTC");

            try {
                // Parse the string to a LocalDate
            	 LocalDate localDate = LocalDate.parse(issued_date, formatter);
            	 ZonedDateTime zonedDateTime = localDate.atStartOfDay(utcZone);
                 LocalDate utcLocalDate = zonedDateTime.toLocalDate();

                 iBookDto.setIssuedDate(utcLocalDate);

            } catch (Exception e) {
                throw new BadRequestException(e.getMessage());
            }
            iBookDto.setMemberId(request.getMemberId());
   
            iBookDtos.add(iBookDto);
            
        }
		return iBookDtos;
	}

	@Override
	public IssuedBookDto mapToDto(IssuedBook iBook) {

		IssuedBookDto iBookDto = new IssuedBookDto();
		iBookDto.setId(iBook.getId());
		iBookDto.setChecked(iBook.isChecked());
		CopiedBookDto copiedBookDto = copiedBookMapper.mapToDto(iBook.getCopiedBook());
		iBookDto.setCopiedBookDto(copiedBookDto);
		iBookDto.setTitle(copiedBookDto.getBookDto().getTitle()); 
		iBookDto.setDueDate(iBook.getDueDate());
		iBookDto.setChecked(iBook.isChecked());
		iBookDto.setExtensionTimes(iBook.getExtensionTimes());
		iBookDto.setIssued(iBook.isIssued());
		iBookDto.setIssuedDate(iBook.getIssuedDate());
		MemberDto memberDto = memberMapper.mapToDto(iBook.getMember());
		iBookDto.setMemberDto(memberDto);
		return iBookDto;
	}

	@Override
	public List<IssuedResponse> mapToResponse(List<IssuedBookDto> iBookDtos) {
		List<IssuedResponse> responses = new ArrayList<>();
		for (IssuedBookDto iBookDto : iBookDtos) {
			IssuedResponse response = new IssuedResponse();
			String title = iBookDto.getCopiedBookDto().getBookDto().getTitle();
			response.setId(iBookDto.getId());
			response.setTitle(title);

			response.setDueDate(iBookDto.getDueDate());
			response.setIssued(iBookDto.isIssued());
			response.setExtensionTimes(iBookDto.getExtensionTimes());
			response.setGeneratedId(iBookDto.getCopiedBookDto().getGeneratedId());

			response.setIssuedDate(iBookDto.getIssuedDate());
			response.setChecked(iBookDto.isChecked());
			response.setMemberId(iBookDto.getMemberDto().getId());
			response.setName(iBookDto.getMemberDto().getName());
			response.setPhone(iBookDto.getMemberDto().getPhone());

			responses.add(response);
		}

		return responses;
	}

	@Override
	public List<IssuedBookDto> mapToDto(List<IssuedBook> iBooks) {
		List<IssuedBookDto> iBookDtos = new ArrayList<>();
		for (IssuedBook iBook : iBooks) {
			IssuedBookDto iBookDto = new IssuedBookDto();
			iBookDto.setId(iBook.getId());
			iBookDto.setChecked(iBook.isChecked());
			CopiedBookDto copiedBookDto = copiedBookMapper.mapToDto(iBook.getCopiedBook());
			iBookDto.setCopiedBookDto(copiedBookDto);
			iBookDto.setDueDate(iBook.getDueDate());
			iBookDto.setExtensionTimes(iBook.getExtensionTimes());
			iBookDto.setIssued(iBook.isIssued());
			iBookDto.setTitle(copiedBookDto.getBookDto().getTitle()); 
			iBookDto.setIssuedDate(iBook.getIssuedDate());
			iBookDto.setChecked(iBook.isChecked());
			MemberDto memberDto = memberMapper.mapToDto(iBook.getMember());
			iBookDto.setMemberDto(memberDto);
			iBookDtos.add(iBookDto);
		}
		return iBookDtos;
	}

}
