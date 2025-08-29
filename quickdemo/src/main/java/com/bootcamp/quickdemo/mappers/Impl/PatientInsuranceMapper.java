package com.bootcamp.quickdemo.mappers.Impl;

import com.bootcamp.quickdemo.dto.PatientInsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.PatientInsuranceResponseDTO;
import com.bootcamp.quickdemo.model.PatientInsuranceModel;
import org.springframework.stereotype.Component;

@Component
public class PatientInsuranceMapper {

    private final InsuranceMapper insuranceMapper;
    
    public PatientInsuranceMapper(InsuranceMapper insuranceMapper) {
        this.insuranceMapper = insuranceMapper;
    }

    public PatientInsuranceModel toEntity(PatientInsuranceRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return PatientInsuranceModel.builder()
                .patientId(dto.getPatientId())
                .insuranceId(dto.getInsuranceId())
                .policyNumber(dto.getPolicyNumber())
                .coverageStart(dto.getCoverageStart())
                .coverageEnd(dto.getCoverageEnd())
                .isPrimary(dto.getIsPrimary())
                .build();
    }
    
    public PatientInsuranceModel updateEntityFromDto(PatientInsuranceRequestDTO dto, PatientInsuranceModel entity) {
        if (dto == null) {
            return entity;
        }
        
        entity.setPatientId(dto.getPatientId());
        entity.setInsuranceId(dto.getInsuranceId());
        entity.setPolicyNumber(dto.getPolicyNumber());
        entity.setCoverageStart(dto.getCoverageStart());
        entity.setCoverageEnd(dto.getCoverageEnd());
        entity.setIsPrimary(dto.getIsPrimary());
        
        return entity;
    }

    public PatientInsuranceResponseDTO toDto(PatientInsuranceModel entity) {
        if (entity == null) {
            return null;
        }
        
        return PatientInsuranceResponseDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .insuranceId(entity.getInsuranceId())
                .policyNumber(entity.getPolicyNumber())
                .coverageStart(entity.getCoverageStart())
                .coverageEnd(entity.getCoverageEnd())
                .isPrimary(entity.getIsPrimary())
                .build();
    }
    
    public PatientInsuranceResponseDTO toDtoWithInsurance(PatientInsuranceModel entity, com.bootcamp.quickdemo.model.InsuranceModel insurance) {
        PatientInsuranceResponseDTO dto = toDto(entity);
        if (dto != null && insurance != null) {
            dto.setInsurance(insuranceMapper.toDto(insurance));
        }
        return dto;
    }
}
