package com.bootcamp.quickdemo.services.impl;

import com.bootcamp.quickdemo.dto.InsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.InsuranceResponseDTO;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.mappers.Impl.InsuranceMapper;
import com.bootcamp.quickdemo.model.InsuranceModel;
import com.bootcamp.quickdemo.model.PatientInsuranceModel;
import com.bootcamp.quickdemo.repository.InsuranceRepository;
import com.bootcamp.quickdemo.repository.PatientInsuranceRepository;
import com.bootcamp.quickdemo.services.InsuranceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientInsuranceRepository patientInsuranceRepository;
    private final InsuranceMapper insuranceMapper;

    public InsuranceServiceImpl(
            InsuranceRepository insuranceRepository,
            PatientInsuranceRepository patientInsuranceRepository,
            InsuranceMapper insuranceMapper) {
        this.insuranceRepository = insuranceRepository;
        this.patientInsuranceRepository = patientInsuranceRepository;
        this.insuranceMapper = insuranceMapper;
    }

    @Override
    public List<InsuranceResponseDTO> getAllInsurances() {
        return insuranceRepository.findAll()
                .stream()
                .map(insuranceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InsuranceResponseDTO getInsuranceById(Long id) {
        InsuranceModel insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance with ID " + id + " not found"));
        
        return insuranceMapper.toDto(insurance);
    }

    @Override
    public InsuranceResponseDTO createInsurance(InsuranceRequestDTO insuranceDto) {
        validateInsurance(insuranceDto);
        
        InsuranceModel insurance = insuranceMapper.toEntity(insuranceDto);
        InsuranceModel savedInsurance = insuranceRepository.save(insurance);
        
        return insuranceMapper.toDto(savedInsurance);
    }

    @Override
    public InsuranceResponseDTO updateInsurance(Long id, InsuranceRequestDTO insuranceDto) {
        validateInsurance(insuranceDto);
        
        InsuranceModel existingInsurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance with ID " + id + " not found"));
        
        InsuranceModel updatedInsurance = insuranceMapper.updateEntityFromDto(insuranceDto, existingInsurance);
        InsuranceModel savedInsurance = insuranceRepository.save(updatedInsurance);
        
        return insuranceMapper.toDto(savedInsurance);
    }

    @Override
    public void deleteInsurance(Long id) {
        if (!insuranceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Insurance with ID " + id + " not found");
        }
        
        insuranceRepository.deleteById(id);
    }
    
    @Override
    public List<InsuranceResponseDTO> getInsurancesByPatient(Long patientId) {
        List<PatientInsuranceModel> patientInsurances = patientInsuranceRepository.findByPatientId(patientId);
        List<InsuranceResponseDTO> result = new ArrayList<>();
        
        for (PatientInsuranceModel pi : patientInsurances) {
            insuranceRepository.findById(pi.getInsuranceId())
                .ifPresent(insurance -> result.add(insuranceMapper.toDto(insurance)));
        }
        
        return result;
    }
    
    private void validateInsurance(InsuranceRequestDTO insurance) {
        if (insurance.getPayerName() == null || insurance.getPayerName().isEmpty()) {
            throw new BadRequestException("Insurance payer name cannot be empty");
        }
    }
}
