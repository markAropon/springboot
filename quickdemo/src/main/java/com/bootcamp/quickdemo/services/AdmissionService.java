package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.AdmissionRequestDTO;
import com.bootcamp.quickdemo.dto.AdmissionResponseDTO;

import java.util.List;
import java.util.Optional;

public interface AdmissionService {
    
    AdmissionResponseDTO createAdmission(AdmissionRequestDTO dto);
    
    List<AdmissionResponseDTO> getAllAdmissions();
    
    Optional<AdmissionResponseDTO> getAdmissionById(Integer id);
    
    AdmissionResponseDTO updateAdmission(Integer id, AdmissionRequestDTO dto);
    
    void deleteAdmission(Integer id);
    
    List<AdmissionResponseDTO> getAdmissionsByPatient(Long patientId);
}
