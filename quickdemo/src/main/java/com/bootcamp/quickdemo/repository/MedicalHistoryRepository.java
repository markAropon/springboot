package com.bootcamp.quickdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.quickdemo.model.MedicalHistoryModel;

import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryModel, Long> {
    List<MedicalHistoryModel> findByPatientId(Long patientId);
}
