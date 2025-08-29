package com.bootcamp.quickdemo.services.impl;

import com.bootcamp.quickdemo.dto.PatientInsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.PatientInsuranceResponseDTO;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.mappers.Impl.PatientInsuranceMapper;
import com.bootcamp.quickdemo.model.InsuranceModel;
import com.bootcamp.quickdemo.model.PatientInsuranceModel;
import com.bootcamp.quickdemo.model.PatientModel;
import com.bootcamp.quickdemo.repository.InsuranceRepository;
import com.bootcamp.quickdemo.repository.PatientInsuranceRepository;
import com.bootcamp.quickdemo.repository.PatientRepository;
import com.bootcamp.quickdemo.services.PatientInsuranceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientInsuranceServiceImpl implements PatientInsuranceService {

    private final PatientInsuranceRepository patientInsuranceRepository;
    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;
    private final PatientInsuranceMapper patientInsuranceMapper;

    public PatientInsuranceServiceImpl(
            PatientInsuranceRepository patientInsuranceRepository,
            PatientRepository patientRepository,
            InsuranceRepository insuranceRepository,
            PatientInsuranceMapper patientInsuranceMapper) {
        this.patientInsuranceRepository = patientInsuranceRepository;
        this.patientRepository = patientRepository;
        this.insuranceRepository = insuranceRepository;
        this.patientInsuranceMapper = patientInsuranceMapper;
    }

    @Override
    public List<PatientInsuranceResponseDTO> getAllPatientInsurances() {
        return patientInsuranceRepository.findAll()
                .stream()
                .map(patientInsuranceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatientInsuranceResponseDTO getPatientInsuranceById(Long id) {
        PatientInsuranceModel patientInsurance = patientInsuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient Insurance with ID " + id + " not found"));
        
        InsuranceModel insurance = insuranceRepository.findById(patientInsurance.getInsuranceId())
                .orElse(null);
        
        return patientInsuranceMapper.toDtoWithInsurance(patientInsurance, insurance);
    }

    @Override
    public List<PatientInsuranceResponseDTO> getPatientInsurancesByPatientId(Long patientId) {
        List<PatientInsuranceModel> patientInsurances = patientInsuranceRepository.findByPatientId(patientId);
        
        return patientInsurances.stream()
                .map(pi -> {
                    InsuranceModel insurance = insuranceRepository.findById(pi.getInsuranceId()).orElse(null);
                    return patientInsuranceMapper.toDtoWithInsurance(pi, insurance);
                })
                .collect(Collectors.toList());
    }

    @Override
    public PatientInsuranceResponseDTO createPatientInsurance(PatientInsuranceRequestDTO dto) {
        validatePatientInsurance(dto);
        
        PatientInsuranceModel patientInsurance = patientInsuranceMapper.toEntity(dto);
        PatientInsuranceModel savedPatientInsurance = patientInsuranceRepository.save(patientInsurance);
        
        InsuranceModel insurance = insuranceRepository.findById(savedPatientInsurance.getInsuranceId()).orElse(null);
        
        return patientInsuranceMapper.toDtoWithInsurance(savedPatientInsurance, insurance);
    }

    @Override
    public PatientInsuranceResponseDTO updatePatientInsurance(Long id, PatientInsuranceRequestDTO dto) {
        validatePatientInsurance(dto);
        
        PatientInsuranceModel existingPatientInsurance = patientInsuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient Insurance with ID " + id + " not found"));
        
        PatientInsuranceModel updatedPatientInsurance = patientInsuranceMapper.updateEntityFromDto(dto, existingPatientInsurance);
        PatientInsuranceModel savedPatientInsurance = patientInsuranceRepository.save(updatedPatientInsurance);
        
        InsuranceModel insurance = insuranceRepository.findById(savedPatientInsurance.getInsuranceId()).orElse(null);
        
        return patientInsuranceMapper.toDtoWithInsurance(savedPatientInsurance, insurance);
    }

    @Override
    public void deletePatientInsurance(Long id) {
        if (!patientInsuranceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient Insurance with ID " + id + " not found");
        }
        
        patientInsuranceRepository.deleteById(id);
    }
    
    private void validatePatientInsurance(PatientInsuranceRequestDTO dto) {
        if (dto.getPatientId() == null) {
            throw new BadRequestException("Patient ID cannot be null");
        }
        
        if (!patientRepository.existsById(dto.getPatientId())) {
            throw new BadRequestException("Patient with ID " + dto.getPatientId() + " does not exist");
        }
        
        if (dto.getInsuranceId() == null) {
            throw new BadRequestException("Insurance ID cannot be null");
        }
        
        if (!insuranceRepository.existsById(dto.getInsuranceId())) {
            throw new BadRequestException("Insurance with ID " + dto.getInsuranceId() + " does not exist");
        }
        
        if (dto.getPolicyNumber() == null || dto.getPolicyNumber().isEmpty()) {
            throw new BadRequestException("Policy number is required");
        }
    }
}
