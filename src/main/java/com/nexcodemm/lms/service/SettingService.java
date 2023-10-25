package com.nexcodemm.lms.service;

import com.nexcodemm.lms.model.response.ApiResponse;
import com.nexcodemm.lms.model.response.SettingResponse;

public interface SettingService {
	
	SettingResponse allData();
	ApiResponse changedBookLimit(int bookLimit);
	ApiResponse changedRentableDays(int rentableDays);
	ApiResponse changedExtendableDays(int extendableDays);
	ApiResponse changedExtendableTimes(int extendableTimes);
	

}
