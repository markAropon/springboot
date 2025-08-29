package com.bootcamp.quickdemo.services;

import java.util.List;

import com.bootcamp.quickdemo.dto.MedicalHistoryDTO;

public interface MedicalHistoryService {
    List<MedicalHistoryDTO> getAll();
    MedicalHistoryDTO getById(Long id);
    List<MedicalHistoryDTO> getByPatient(Long patientId);
    MedicalHistoryDTO create(MedicalHistoryDTO dto);
    MedicalHistoryDTO update(Long id, MedicalHistoryDTO dto);
    void delete(Long id);
}
