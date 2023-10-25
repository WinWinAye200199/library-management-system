package com.nexcodemm.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexcodemm.lms.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	@Query("SELECT r FROM Role r where r.name = :name")
	Role findByName(@Param("name")String name);
}
