package com.nexcodemm.lms.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "setting")
public class Setting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "book_limit",columnDefinition = "INTEGER DEFAULT 0")
	private int bookLimits;
	
	@Column(name = "rentable_days",columnDefinition = "INTEGER DEFAULT 0")
	private int rentableDays;
	
	@Column(name = "extendable_days",columnDefinition = "INTEGER DEFAULT 0")
	private int extendableDays;
	
	@Column(name = "extendable_times",columnDefinition = "INTEGER DEFAULT 0")
	private int extendableTimes;

}
