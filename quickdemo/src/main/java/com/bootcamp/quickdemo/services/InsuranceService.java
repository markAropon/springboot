package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.InsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.InsuranceResponseDTO;

import java.util.List;

public interface InsuranceService {
    List<InsuranceResponseDTO> getAllInsurances();
    InsuranceResponseDTO getInsuranceById(Long id);
    InsuranceResponseDTO createInsurance(InsuranceRequestDTO insuranceDto);
    InsuranceResponseDTO updateInsurance(Long id, InsuranceRequestDTO insuranceDto);
    void deleteInsurance(Long id);
    List<InsuranceResponseDTO> getInsurancesByPatient(Long patientId);
}
