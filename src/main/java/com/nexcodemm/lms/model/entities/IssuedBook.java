package com.nexcodemm.lms.model.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "issued_books")
@Getter
@Setter
public class IssuedBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "issued_date")
	private LocalDate issuedDate;
	
	@Column(name = "due_date")
	private LocalDate dueDate;
	
	@Column(name = "extension_times")
	private int extensionTimes;
	
	@Column(name = "is_issued")
	private boolean issued;
	
	@Column(name = "is_checked")
	private boolean checked;
	
	@ManyToOne
	@JoinColumn(name = "copied_book_id")
	private CopiedBook copiedBook;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
}
