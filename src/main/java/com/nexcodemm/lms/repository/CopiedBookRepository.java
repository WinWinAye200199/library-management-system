package com.nexcodemm.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexcodemm.lms.model.entities.CopiedBook;


public interface CopiedBookRepository extends JpaRepository<CopiedBook, Long>{
	
	@Query("SELECT c FROM CopiedBook c WHERE c.book.id = :bookId")
	List<CopiedBook> findByBookId(@Param("bookId") long bookId);
	
	CopiedBook findByGeneratedId(String generatedId);
	
	@Query("SELECT c FROM CopiedBook c WHERE c.issued = :issued")
	List<CopiedBook> findByIssued(@Param("issued") boolean issued);
}
