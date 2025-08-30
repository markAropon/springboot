package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.PatientRequestDTO;
import com.bootcamp.quickdemo.dto.PatientResponseDTO;
import java.util.List;

public interface PatientService {
    List<PatientResponseDTO> getAllPatients();
    org.springframework.data.domain.Page<PatientResponseDTO> getPatients(int page, int size, String name, Integer age);
    PatientResponseDTO getPatientById(Long id);
    PatientResponseDTO createPatient(PatientRequestDTO patientDto);
    PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientDto);
    void deletePatient(Long id);
}
