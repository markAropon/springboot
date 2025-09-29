package com.bootcamp.quickdemo.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.bootcamp.quickdemo.dto.AdmissionRequestDTO;
import com.bootcamp.quickdemo.dto.AdmissionResponseDTO;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.mappers.Impl.AdmissionMapper;
import com.bootcamp.quickdemo.model.AdmissionModel;
import com.bootcamp.quickdemo.repository.AdmissionRepository;
import com.bootcamp.quickdemo.services.AdmissionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final AdmissionMapper admissionMapper;

    @Override
    public AdmissionResponseDTO createAdmission(AdmissionRequestDTO dto) {
        if (dto.getPatientId() == null || dto.getDoctorId() == null || dto.getPurposeId() == null) {
            throw new BadRequestException("Patient ID, Doctor ID, and Purpose ID are required fields");
        }
        
        AdmissionModel admission = admissionMapper.toEntity(dto);
        AdmissionModel saved = admissionRepository.save(admission);
        return admissionMapper.toResponseDTO(saved);
    }

    @Override
    public List<AdmissionResponseDTO> getAllAdmissions() {
        return admissionRepository.findAll()
                .stream()
                .map(admissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }



    @Override
    public AdmissionResponseDTO updateAdmission(Integer id, AdmissionRequestDTO dto) {
        AdmissionModel existing = admissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found with ID: " + id));
                
        if (dto.getPatientId() == null || dto.getDoctorId() == null || dto.getPurposeId() == null) {
            throw new BadRequestException("Patient ID, Doctor ID, and Purpose ID are required fields");
        }
        
        // Update the existing entity with values from DTO
        existing.setPatientId(dto.getPatientId());
        existing.setDoctorId(dto.getDoctorId());
        existing.setPurposeId(dto.getPurposeId());
        existing.setAdmissionDate(dto.getAdmissionDate());
        existing.setDischargeDate(dto.getDischargeDate());
        existing.setStatus(dto.getStatus());
        existing.setRoomNumber(dto.getRoomNumber());

        return admissionMapper.toResponseDTO(admissionRepository.save(existing));
    }

    @Override
    public void deleteAdmission(Integer id) {
        if (!admissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admission not found with ID: " + id);
        }
        admissionRepository.deleteById(id);
    }
    
    @Override
    public List<AdmissionResponseDTO> getAdmissionsByPatient(Long patientId) {
        return admissionRepository.findByPatientId(patientId)
                .stream()
                .map(admissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AdmissionResponseDTO> getAdmissionById(Integer id) {
          return Optional.ofNullable(admissionRepository.findById(id)
                .map(admissionMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found with ID: " + id)));
    }
}
