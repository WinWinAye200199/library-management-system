package com.nexcodemm.lms.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexcodemm.lms.model.entities.IssuedBook;


public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long>{
    @Query("SELECT ib FROM IssuedBook ib WHERE ib.issuedDate >= :startDate AND ib.issuedDate <= :endDate ORDER BY ib.issued DESC, ib.id DESC")
    List<IssuedBook> findEntitiesWithinMonth(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    @Query("SELECT ib FROM IssuedBook ib WHERE ib.issuedDate >= :startDate AND ib.issuedDate <= :endDate AND ib.issued = :issued ORDER BY ib.id DESC")
    List<IssuedBook> findAllIssued(
    		@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("issued") boolean issued
    );
    @Query("SELECT ib FROM IssuedBook ib WHERE ib.issued = true")
    List<IssuedBook> findByIssuedTrue();
    @Query("SELECT ib FROM IssuedBook ib WHERE ib.checked = true AND ib.issuedDate <= :currentDate")
    List<IssuedBook> findCheckedBooksToReset(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT ib FROM IssuedBook ib WHERE ib.copiedBook.id = :id")
    IssuedBook findByCopiedBookId(@Param("id")long id);
    
    @Query("SELECT ib FROM IssuedBook ib WHERE ib.member.id = :id")
    List<IssuedBook> findByMemberId(@Param("id")long id);
}
