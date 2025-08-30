package com.bootcamp.quickdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.quickdemo.model.PatientModel;

public interface PatientRepository extends JpaRepository<PatientModel, Long> {
	// Optimized: Paginated and filtered queries
	org.springframework.data.domain.Page<PatientModel> findByNameContainingIgnoreCase(String name, org.springframework.data.domain.Pageable pageable);
	org.springframework.data.domain.Page<PatientModel> findByNameContainingIgnoreCaseAndPhoneContaining(String name, String phone, org.springframework.data.domain.Pageable pageable);
}
