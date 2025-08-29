package com.bootcamp.quickdemo.services.impl;

import com.bootcamp.quickdemo.dto.VitalSignRequestDTO;
import com.bootcamp.quickdemo.dto.VitalSignResponseDTO;
import com.bootcamp.quickdemo.exception.BadRequestException;
import com.bootcamp.quickdemo.exception.ResourceNotFoundException;
import com.bootcamp.quickdemo.mappers.Impl.VitalSignMapper;
import com.bootcamp.quickdemo.model.VitalSignModel;
import com.bootcamp.quickdemo.repository.AdmissionRepository;
import com.bootcamp.quickdemo.repository.VitalSignRepository;
import com.bootcamp.quickdemo.services.VitalSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VitalSignServiceImpl implements VitalSignService {

    private final VitalSignRepository vitalSignRepository;
    private final AdmissionRepository admissionRepository;
    private final VitalSignMapper vitalSignMapper;

    @Override
    public List<VitalSignResponseDTO> getAllVitalSigns() {
        return vitalSignRepository.findAll()
                .stream()
                .map(vitalSignMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VitalSignResponseDTO getVitalSignById(Long id) {
        VitalSignModel vitalSign = vitalSignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vital Sign with ID " + id + " not found"));
        
        return vitalSignMapper.toDto(vitalSign);
    }

    @Override
    public VitalSignResponseDTO createVitalSign(VitalSignRequestDTO dto) {
        validateVitalSign(dto);
        
        if (dto.getRecordedDate() == null) {
            dto.setRecordedDate(LocalDateTime.now());
        }
        
        VitalSignModel vitalSign = vitalSignMapper.toEntity(dto);
        VitalSignModel savedVitalSign = vitalSignRepository.save(vitalSign);
        
        return vitalSignMapper.toDto(savedVitalSign);
    }

    @Override
    public List<VitalSignResponseDTO> getVitalSignsByAdmission(Integer admissionId) {
        if (!admissionRepository.existsById(admissionId)) {
            throw new ResourceNotFoundException("Admission with ID " + admissionId + " not found");
        }
        
        return vitalSignRepository.findByAdmissionId(admissionId)
                .stream()
                .map(vitalSignMapper::toDto)
                .collect(Collectors.toList());
    }
    
    private void validateVitalSign(VitalSignRequestDTO dto) {
        if (dto.getAdmissionId() == null) {
            throw new BadRequestException("Admission ID is required");
        }
        
        if (!admissionRepository.existsById(dto.getAdmissionId())) {
            throw new ResourceNotFoundException("Admission with ID " + dto.getAdmissionId() + " not found");
        }
    }
}
