package com.nexcodemm.lms.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "total_books")
	private int totalBooks;
	
	@Column(name = "leftover_books")
	private int leftoverBooks;
	
	@Column(name = "total_issued_books")
	private int totalIssuedBooks;
	
	@Column(name = "damaged_books")
	private int damagedBooks;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CopiedBook> copiedBookLists;

}

