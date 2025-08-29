package com.bootcamp.quickdemo.services.impl;

import com.bootcamp.quickdemo.dto.PatientRequestDTO;
import com.bootcamp.quickdemo.dto.PatientResponseDTO;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.mappers.Impl.PatientMapper;
import com.bootcamp.quickdemo.model.PatientModel;
import com.bootcamp.quickdemo.repository.PatientRepository;
import com.bootcamp.quickdemo.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {
        PatientModel patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + id + " not found"));
        
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientDto) {
        validatePatient(patientDto);
        
        PatientModel patient = patientMapper.toEntity(patientDto);
        PatientModel savedPatient = patientRepository.save(patient);
        
        return patientMapper.toDto(savedPatient);
    }

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientDto) {
        validatePatient(patientDto);
        
        PatientModel existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + id + " not found"));
        
        PatientModel updatedPatient = patientMapper.updateEntityFromDto(patientDto, existingPatient);
        PatientModel savedPatient = patientRepository.save(updatedPatient);
        
        return patientMapper.toDto(savedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient with ID " + id + " not found");
        }
        
        patientRepository.deleteById(id);
    }
    
    private void validatePatient(PatientRequestDTO patient) {
        if (patient.getName() == null || patient.getName().isEmpty()) {
            throw new BadRequestException("Patient name cannot be empty");
        }
        
        if (patient.getPhone() == null || patient.getPhone().isEmpty()) {
            throw new BadRequestException("Phone number is required");
        }
    }
}
