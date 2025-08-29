package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.PatientInsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.PatientInsuranceResponseDTO;
import java.util.List;

public interface PatientInsuranceService {
    List<PatientInsuranceResponseDTO> getAllPatientInsurances();
    PatientInsuranceResponseDTO getPatientInsuranceById(Long id);
    List<PatientInsuranceResponseDTO> getPatientInsurancesByPatientId(Long patientId);
    PatientInsuranceResponseDTO createPatientInsurance(PatientInsuranceRequestDTO dto);
    PatientInsuranceResponseDTO updatePatientInsurance(Long id, PatientInsuranceRequestDTO dto);
    void deletePatientInsurance(Long id);
}
