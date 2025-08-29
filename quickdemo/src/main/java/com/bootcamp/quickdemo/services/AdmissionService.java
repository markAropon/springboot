package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.AdmissionRequestDTO;
import com.bootcamp.quickdemo.dto.AdmissionResponseDTO;
import java.util.List;

public interface AdmissionService {
    AdmissionResponseDTO createAdmission(AdmissionRequestDTO dto);
    List<AdmissionResponseDTO> getAllAdmissions();
    AdmissionResponseDTO getAdmissionById(Integer id);
    AdmissionResponseDTO updateAdmission(Integer id, AdmissionRequestDTO dto);
    void deleteAdmission(Integer id);
    List<AdmissionResponseDTO> getAdmissionsByPatient(Long patientId);
}
