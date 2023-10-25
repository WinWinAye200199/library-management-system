package com.nexcodemm.lms.service.Impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nexcodemm.lms.mapper.CopiedBookMapper;
import com.nexcodemm.lms.mapper.IssuedBookMapper;
import com.nexcodemm.lms.model.dto.BookDto;
import com.nexcodemm.lms.model.dto.CopiedBookDto;
import com.nexcodemm.lms.model.dto.IssuedBookDto;
import com.nexcodemm.lms.model.entities.CopiedBook;
import com.nexcodemm.lms.model.entities.IssuedBook;
import com.nexcodemm.lms.model.entities.Member;
import com.nexcodemm.lms.model.excepiton.BadRequestException;
import com.nexcodemm.lms.model.excepiton.NotFoundException;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.SettingResponse;
import com.nexcodemm.lms.repository.CopiedBookRepository;
import com.nexcodemm.lms.repository.IssuedBookRepository;
import com.nexcodemm.lms.repository.MemberRepository;
import com.nexcodemm.lms.service.BookService;
import com.nexcodemm.lms.service.CopiedBookService;
import com.nexcodemm.lms.service.IssuedBookService;
import com.nexcodemm.lms.service.MemberService;
import com.nexcodemm.lms.service.SettingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssuedBookServiceImpl implements IssuedBookService {

	private final MemberRepository memberRepository;

	private final CopiedBookRepository copiedBookRepository;

	private final IssuedBookRepository issuedBookRepository;

	private final BookService bookService;

	private final IssuedBookMapper issuedBookMapper;

	private final CopiedBookMapper copiedBookMapper;

	private final CopiedBookService copiedBookService;

	private final MemberService memberService;

	private final SettingService settingService;

	@Override
	public ApiResponse createIssuedBook(List<IssuedBookDto> iBookDtos) {
		// List<IssuedBookDto> responses = new ArrayList<>();
		// List<String> generatedIds = new ArrayList<>();
		SettingResponse setting = settingService.allData();
		int rentableDays = setting.getRentableDays();
		int bookLimit = setting.getBookLimit();
		for (IssuedBookDto iBookDto : iBookDtos) {
			String generatedId = iBookDto.getGeneratedId();
			Long memberId = iBookDto.getMemberId();

			Member member = memberRepository.findById(memberId)
					.orElseThrow(() -> new NotFoundException("Member not found with ID: " + memberId));
			CopiedBook cBook = copiedBookRepository.findByGeneratedId(generatedId);
			long bookId = cBook.getBook().getId();
			BookDto book = bookService.findById(bookId);
			int leftover = book.getLeftoverBooks();

			if (leftover != 0) {
				if (member.getTotalIssued() < bookLimit) {

					IssuedBook iBook = new IssuedBook();
					iBook.setChecked(false);
					iBook.setIssued(true);
					iBook.setExtensionTimes(0);
					iBook.setCopiedBook(cBook);
					iBook.setMember(member);
					iBook.setIssuedDate(iBookDto.getIssuedDate());

					// Calculate due date using java.time classes
					LocalDate issuedDate = iBookDto.getIssuedDate();
					LocalDate dueDate = issuedDate.plusDays(rentableDays);

					iBook.setDueDate(dueDate);
					CopiedBookDto cBookDto = copiedBookMapper.mapToDto(cBook);

					copiedBookService.updateAvailability(cBookDto); // Mark the copied book as issued

					// long bookId = cBookDto.getBookId();
					bookService.incrementTotalIssuedBook(bookId);
					bookService.decrementLeftoverBook(bookId);
					long id = member.getId();
					memberService.incrementIssuedBooks(id); // Increment issued book count for the member

					issuedBookRepository.save(iBook);
					// IssuedBook savedIBook =
					// IssuedBookDto issuedDto = issuedBookMapper.mapToDto(savedIBook);
					// responses.add(issuedDto);

				} else {
					throw new BadRequestException("Book Limitation is full!");
				}

			} else {
				throw new BadRequestException("Leftover Books are over!");
			}
		}

		return new ApiResponse(true, "Issued Books Created Successfully!");

	}

	@Override
	public ApiResponse renew(List<Long> iBookIds) {

		List<String> lists = new ArrayList<>();
		for (Long iBookId : iBookIds) {
			SettingResponse setting = settingService.allData();
			int extendableTimes = setting.getExtendableTimes();
			IssuedBook iBook = issuedBookRepository.getById(iBookId);
			int times = iBook.getExtensionTimes();
			if (times >= extendableTimes) {
				lists.add(iBook.getCopiedBook().getGeneratedId());
			}

		}
		if (lists.size() > 1) {
			throw new BadRequestException(lists.toString() + " are exceeded Book Extension Times!");
		} else if (lists.size() == 1) {
			throw new BadRequestException(lists.toString() + " is exceeded Book Extension Times!");
		}
		for (Long iBookId : iBookIds) {
			SettingResponse setting = settingService.allData();
			int extendableTimes = setting.getExtendableTimes();
			int extendableDays = setting.getExtendableDays();
			IssuedBook iBook = issuedBookRepository.getById(iBookId);
			int times = iBook.getExtensionTimes();
			if (times < extendableTimes) {
				iBook.setExtensionTimes(times + 1);
				LocalDate dueDate = iBook.getDueDate();
				LocalDate updatedDueDate = dueDate.plusDays(extendableDays);
				iBook.setDueDate(updatedDueDate);
				issuedBookRepository.save(iBook);
			}

		}

		return new ApiResponse(true, "Successfully Renew!");
	}

	@Override
	public ApiResponse returnBook(List<Long> iBookIds) {

		for (Long iBookId : iBookIds) {

			IssuedBook iBook = issuedBookRepository.getById(iBookId);
			if (iBook.isIssued() == true) {
				iBook.setIssued(false);

				CopiedBook cbookDto = iBook.getCopiedBook();
				cbookDto.setIssued(false);
				long bookId = cbookDto.getBook().getId();
				bookService.returnBook(bookId);

				long memberId = iBook.getMember().getId();
				memberService.decrementIssuedBooks(memberId);

				issuedBookRepository.save(iBook);
			} else {
				throw new BadRequestException("Already Returned Book!");
			}

		}
		return new ApiResponse(true, "Successfully Return!");
	}

	@Override
	public List<IssuedBookDto> findAllIssued(int year, int month, String status) {
		List<IssuedBook> iBooks=null;
		List<IssuedBookDto> iBookDtos =null;

		if (status.equalsIgnoreCase("All")) {
			LocalDate startDate = LocalDate.of(year, month, 1);
			// Calculate the last day of the month
			YearMonth yearMonth = YearMonth.of(year, month);
			LocalDate endDate = yearMonth.atEndOfMonth();
		     iBooks = issuedBookRepository.findEntitiesWithinMonth(startDate, endDate);

		} else if (status.equalsIgnoreCase("Issued")) {
			LocalDate startDate = LocalDate.of(year, month, 1);

			// Calculate the last day of the month
			YearMonth yearMonth = YearMonth.of(year, month);
			LocalDate endDate = yearMonth.atEndOfMonth();
			iBooks = issuedBookRepository.findAllIssued(startDate, endDate, true);

		} else if (status.equalsIgnoreCase("Returned")) {
			LocalDate startDate = LocalDate.of(year, month, 1);

			// Calculate the last day of the month
			YearMonth yearMonth = YearMonth.of(year, month);
			LocalDate endDate = yearMonth.atEndOfMonth();
			 iBooks = issuedBookRepository.findAllIssued(startDate, endDate, false);

			
		}
		 iBookDtos = issuedBookMapper.mapToDto(iBooks);

		return iBookDtos;

	}

	@Override
	public List<IssuedBookDto> calculateOverdueBooks() {
		List<IssuedBook> unreturnedBooks = issuedBookRepository.findByIssuedTrue();
		LocalDate currentDate = LocalDate.now();

		List<IssuedBook> overdueBooks = new ArrayList<>();

		for (IssuedBook ibook : unreturnedBooks) {
			if (currentDate.isAfter(ibook.getDueDate())) {
				overdueBooks.add(ibook);
			}
		}

		List<IssuedBookDto> responses = issuedBookMapper.mapToDto(overdueBooks);

		return responses;

	}

	@Override
	public ApiResponse setCheckedToTrue(List<Long> iBookIds) {
		for (Long iBookId : iBookIds) {
			IssuedBook iBook = issuedBookRepository.getById(iBookId);
			iBook.setChecked(true);
			issuedBookRepository.save(iBook);
		}
		return new ApiResponse(true, "Contact Successfully!");
	}

	@Override
	@Scheduled(fixedRate = 60 * 1000 * 24 * 60) // 24 hours 60 * 1000
	public ApiResponse resetCheckedFlag() {
		LocalDate currentDate = LocalDate.now(); // Subtract 1 day to represent 24 hours ago

		List<IssuedBook> checkedBooks = issuedBookRepository.findCheckedBooksToReset(currentDate);

		for (IssuedBook iBook : checkedBooks) {
			iBook.setChecked(false);
			issuedBookRepository.save(iBook);
		}

		return new ApiResponse(true, "Done!");
	}

	@Override
	public List<IssuedBookDto> getAllIssuedBooks() {
		List<IssuedBook> iBooks = issuedBookRepository.findAll();
		List<IssuedBookDto> iBookDtos = issuedBookMapper.mapToDto(iBooks);
		return iBookDtos;
	}

	@Override
	public List<IssuedBookDto> getMembersByIssued(long id) {
		List<IssuedBook> iBooks = issuedBookRepository.findByMemberId(id);
		List<IssuedBookDto> iBookDtos = issuedBookMapper.mapToDto(iBooks);
		return iBookDtos;
	}

//	@Override
//	public List<IssuedBookDto> findAll(String status) {
//		
//		if(status .equalsIgnoreCase("All")) {
//			List<IssuedBook> iBooks = issuedBookRepository.findAll();
//			List<IssuedBookDto> iBookDtos = issuedBookMapper.mapToDto(iBooks);
//			
//			return iBookDtos;
//		}else if(status.equalsIgnoreCase("Issued")) {
//			boolean issued = true;
//			List<IssuedBook> iBooks = issuedBookRepository.findAllIssued(issued);
//			List<IssuedBookDto> iBookDtos = issuedBookMapper.mapToDto(iBooks);
//			
//			return iBookDtos;
//		}else if(status.equalsIgnoreCase("Returned")) {
//			boolean issued = false;
//			List<IssuedBook> iBooks = issuedBookRepository.findAllIssued(issued);
//			List<IssuedBookDto> iBookDtos = issuedBookMapper.mapToDto(iBooks);
//			
//			return iBookDtos;
//		}
//		return null;
//	}

}
