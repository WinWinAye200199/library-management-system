package com.nexcodemm.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexcodemm.lms.model.entities.Setting;

public interface SettingRepository extends JpaRepository<Setting, Long>{
	
}
