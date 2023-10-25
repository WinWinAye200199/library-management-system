package com.nexcodemm.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexcodemm.lms.model.entities.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	@Query("SELECT m FROM Member m WHERE m.phone = :phone")
	Member findByPhoneNumber(@Param("phone") String phone);
	
	@Query("SELECT m FROM Member m WHERE m.totalIssued > :issued")
	List<Member> findByTotalIssued(@Param("issued")int issued);
	
	@Query("SELECT m FROM Member m WHERE NOT m.id = :id")
	List<Member> findAll(@Param("id")long id);
}
