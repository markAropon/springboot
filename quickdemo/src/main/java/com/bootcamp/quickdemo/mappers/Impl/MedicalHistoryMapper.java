package com.bootcamp.quickdemo.mappers.Impl;

import org.springframework.stereotype.Component;

import com.bootcamp.quickdemo.dto.MedicalHistoryDTO;
import com.bootcamp.quickdemo.model.MedicalHistoryModel;

@Component
public class MedicalHistoryMapper {

    public MedicalHistoryDTO toDto(MedicalHistoryModel entity) {
        if (entity == null) {
            return null;
        }

        MedicalHistoryDTO dto = new MedicalHistoryDTO();
        dto.setId(entity.getId());
        dto.setPatientId(entity.getPatientId());
        dto.setCondition(entity.getCondition());
        dto.setIcd10Code(entity.getIcd10Code());
        dto.setDiagnosedDate(entity.getDiagnosedDate());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    public MedicalHistoryModel toEntity(MedicalHistoryDTO dto) {
        if (dto == null) {
            return null;
        }

        MedicalHistoryModel entity = new MedicalHistoryModel();
        entity.setId(dto.getId());
        entity.setCondition(dto.getCondition());
        entity.setIcd10Code(dto.getIcd10Code());
        entity.setDiagnosedDate(dto.getDiagnosedDate());
        entity.setStatus(dto.getStatus());

       

        return entity;
    }
}
