package com.bootcamp.quickdemo.repository;

import com.bootcamp.quickdemo.model.PatientInsuranceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PatientInsuranceRepository extends JpaRepository<PatientInsuranceModel, Long> {
    List<PatientInsuranceModel> findByPatientId(Long patientId);
}
