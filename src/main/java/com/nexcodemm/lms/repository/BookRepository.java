package com.nexcodemm.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexcodemm.lms.model.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	List<Book> findByTitle(String title);
	
	@Query("SELECT b from Book b ORDER BY id DESC")
	List<Book> findAll();
	
	@Query("SELECT b FROM Book b WHERE NOT b.id = :id")
	List<Book> findAll(@Param("id")long id);

}
