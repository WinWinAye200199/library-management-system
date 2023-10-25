package com.nexcodemm.lms.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingDto {
	
	private long id;
	private int bookLimit;
	private int rentableDays;
	private int extendableDays;
	private int extendableTimes;

}
