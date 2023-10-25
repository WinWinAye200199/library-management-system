package com.nexcodemm.lms.model.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "members")
@Getter
@Setter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;

    @Column(name = "phone", unique = true)
	private String phone;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "total_issued")
	private int totalIssued;
	
	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
	private List<IssuedBook> issuedBookLists;
}

