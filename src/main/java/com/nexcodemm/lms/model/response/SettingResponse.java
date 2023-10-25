package com.nexcodemm.lms.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingResponse {
	
	private int bookLimit;
	private int rentableDays;
	private int extendableDays;
	private int extendableTimes;

}
