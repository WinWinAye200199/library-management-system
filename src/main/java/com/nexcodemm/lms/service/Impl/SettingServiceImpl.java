package com.nexcodemm.lms.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nexcodemm.lms.model.entities.Setting;
import com.nexcodemm.lms.model.excepiton.BadRequestException;
import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.SettingResponse;
import com.nexcodemm.lms.repository.SettingRepository;
import com.nexcodemm.lms.service.SettingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SettingServiceImpl implements SettingService {

	private final SettingRepository settingRepository;

	@Override
	public SettingResponse allData() {
		List<Setting> datas = settingRepository.findAll();
		SettingResponse response = new SettingResponse();
		for (Setting data : datas) {

			response.setBookLimit(data.getBookLimits());
			response.setRentableDays(data.getRentableDays());
			response.setExtendableDays(data.getExtendableDays());
			response.setExtendableTimes(data.getExtendableTimes());

		}

		return response;
	}

	@Override
	public ApiResponse changedBookLimit(int bookLimit) {

		Setting setting = settingRepository.findById(1L)
				.orElseThrow(() -> new BadRequestException("Default Setting does not exist!"));

		setting.setBookLimits(bookLimit);
		settingRepository.save(setting);

		return new ApiResponse(true, "Updated Book Limit!");

	}

	@Override
	public ApiResponse changedRentableDays(int rentableDays) {

		Setting setting = settingRepository.findById(1L)
				.orElseThrow(() -> new BadRequestException("Default Setting does not exist!"));

		setting.setRentableDays(rentableDays);
		settingRepository.save(setting);

		return new ApiResponse(true, "Updated Rentable Days!");

	}

	@Override
	public ApiResponse changedExtendableDays(int extendableDays) {

		Setting setting = settingRepository.findById(1L)
				.orElseThrow(() -> new BadRequestException("Default Setting does not exist!"));

		setting.setExtendableDays(extendableDays);
		settingRepository.save(setting);

		return new ApiResponse(true, "Updated Extendable Days!");

	}

	@Override
	public ApiResponse changedExtendableTimes(int extendableTimes) {

		Setting setting = settingRepository.findById(1L)
				.orElseThrow(() -> new BadRequestException("Default Setting does not exist!"));

		setting.setExtendableTimes(extendableTimes);
		settingRepository.save(setting);

		return new ApiResponse(true, "Updated Extendable Times!");

	}

}
