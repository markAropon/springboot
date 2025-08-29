package com.bootcamp.quickdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.quickdemo.model.PatientModel;

public interface PatientRepository extends JpaRepository<PatientModel, Long> {
    
}
