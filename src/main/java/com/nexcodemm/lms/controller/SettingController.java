package com.nexcodemm.lms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexcodemm.lms.model.request.ChangedSettingRequest;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.SettingResponse;
import com.nexcodemm.lms.service.SettingService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/settings")
public class SettingController {
	
	
	private final SettingService settingService;
	

	@GetMapping
	public SettingResponse getAlldatas() {
		SettingResponse response = settingService.allData();
		
		return response;
	}
	
	@PutMapping("/change-book-limit")
	public ApiResponse changeBookLimits(@RequestBody ChangedSettingRequest request) {
		
		int bookLimit = request.getLimit();
		
		ApiResponse changedLimit = settingService.changedBookLimit(bookLimit);
		
		return changedLimit;
		
	}
	
	@PutMapping("/change-rentable-days")
	public ApiResponse changeRentableDays(@RequestBody ChangedSettingRequest request) {
		
		int rentableDays = request.getLimit();
		ApiResponse changedLimit = settingService.changedRentableDays(rentableDays);
		
		return changedLimit;
		
	}
	
	@PutMapping("/change-extendable-days")
	public ApiResponse changeExtendableDays(@RequestBody ChangedSettingRequest request) {
	
		int extendableDays = request.getLimit();
		ApiResponse changedLimit = settingService.changedExtendableDays(extendableDays);
		
		return changedLimit;
		
	}
	
	@PutMapping("/change-extendable-times")
	public ApiResponse changeExtendableTimes(@RequestBody ChangedSettingRequest request) {
		
		int extendableTimes = request.getLimit();
		ApiResponse changedLimit = settingService.changedExtendableTimes(extendableTimes);
		
		return changedLimit;
		
	}
	
}
