package com.bootcamp.quickdemo.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.bootcamp.quickdemo.dto.MedicalHistoryDTO;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.mappers.Impl.MedicalHistoryMapper;
import com.bootcamp.quickdemo.model.MedicalHistoryModel;
import com.bootcamp.quickdemo.repository.MedicalHistoryRepository;
import com.bootcamp.quickdemo.repository.PatientRepository;
import com.bootcamp.quickdemo.services.MedicalHistoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PatientRepository patientRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;

    @Override
    public List<MedicalHistoryDTO> getAll() {
        return medicalHistoryRepository.findAll()
                .stream()
                .map(medicalHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalHistoryDTO getById(Long id) {
        return medicalHistoryRepository.findById(id)
                .map(medicalHistoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Medical history record not found with ID: " + id));
    }
    @Override
    public List<MedicalHistoryDTO> getByPatient(Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with ID: " + patientId);
        }
        
        return medicalHistoryRepository.findByPatientId(patientId)
                .stream()
                .map(medicalHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalHistoryDTO create(MedicalHistoryDTO dto) {

        if (dto.getPatientId() == null) {
            throw new BadRequestException("Patient ID is required");
        }
        if (dto.getCondition() == null || dto.getCondition().trim().isEmpty()) {
            throw new BadRequestException("Medical condition is required");
        }

        if (!patientRepository.existsById(dto.getPatientId())) {
            throw new ResourceNotFoundException("Patient not found with ID: " + dto.getPatientId());
        }
        
        MedicalHistoryModel medicalHistory = medicalHistoryMapper.toEntity(dto);
        MedicalHistoryModel saved = medicalHistoryRepository.save(medicalHistory);
        return medicalHistoryMapper.toDto(saved);
    }

    @Override
    public MedicalHistoryDTO update(Long id, MedicalHistoryDTO dto) {

        MedicalHistoryModel existing = medicalHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical history record not found with ID: " + id));

        if (dto.getPatientId() == null) {
            throw new BadRequestException("Patient ID is required");
        }
        if (dto.getCondition() == null || dto.getCondition().trim().isEmpty()) {
            throw new BadRequestException("Medical condition is required");
        }
        if (!existing.getPatientId().equals(dto.getPatientId()) && 
                !patientRepository.existsById(dto.getPatientId())) {
            throw new ResourceNotFoundException("Patient not found with ID: " + dto.getPatientId());
        }
    
        existing.setPatientId(dto.getPatientId());
        existing.setCondition(dto.getCondition());
        existing.setIcd10Code(dto.getIcd10Code());
        existing.setDiagnosedDate(dto.getDiagnosedDate());
        existing.setStatus(dto.getStatus());
        
        MedicalHistoryModel updated = medicalHistoryRepository.save(existing);
        return medicalHistoryMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!medicalHistoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medical history record not found with ID: " + id);
        }
        medicalHistoryRepository.deleteById(id);
    }
}
