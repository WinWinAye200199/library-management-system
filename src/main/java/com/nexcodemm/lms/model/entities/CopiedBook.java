package com.nexcodemm.lms.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "copied_books")
@Getter
@Setter
public class CopiedBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "generated_id")
	private String generatedId;
	
	@Column(name = "is_damaged")
	private boolean damaged;
	
	@Column(name = "is_issued")
	private boolean issued;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@OneToMany(mappedBy = "copiedBook", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
	private List<IssuedBook> issuedBookLists;
	
}
