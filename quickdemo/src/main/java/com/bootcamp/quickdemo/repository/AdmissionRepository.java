package com.bootcamp.quickdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.bootcamp.quickdemo.model.AdmissionModel;

public interface AdmissionRepository extends JpaRepository<AdmissionModel, Integer> {
    List<AdmissionModel> findByPatientId(Long patientId);
}
