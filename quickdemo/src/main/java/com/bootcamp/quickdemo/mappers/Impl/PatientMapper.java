package com.bootcamp.quickdemo.mappers.Impl;

import com.bootcamp.quickdemo.dto.PatientRequestDTO;
import com.bootcamp.quickdemo.dto.PatientResponseDTO;
import com.bootcamp.quickdemo.model.PatientModel;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientModel toEntity(PatientRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return PatientModel.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .insurance(dto.getInsurance())
                .build();
    }
    
    public PatientModel updateEntityFromDto(PatientRequestDTO dto, PatientModel entity) {
        if (dto == null) {
            return entity;
        }
        
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setInsurance(dto.getInsurance());
        
        return entity;
    }

    public PatientResponseDTO toDto(PatientModel entity) {
        if (entity == null) {
            return null;
        }
        
        return PatientResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .insurance(entity.getInsurance())
                .build();
    }
}
