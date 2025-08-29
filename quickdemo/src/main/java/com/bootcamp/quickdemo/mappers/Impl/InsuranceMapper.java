package com.bootcamp.quickdemo.mappers.Impl;

import com.bootcamp.quickdemo.dto.InsuranceRequestDTO;
import com.bootcamp.quickdemo.dto.InsuranceResponseDTO;
import com.bootcamp.quickdemo.model.InsuranceModel;
import org.springframework.stereotype.Component;

@Component
public class InsuranceMapper {

    public InsuranceModel toEntity(InsuranceRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return InsuranceModel.builder()
                .payerName(dto.getPayerName())
                .payerCode(dto.getPayerCode())
                .build();
    }
    
    public InsuranceModel updateEntityFromDto(InsuranceRequestDTO dto, InsuranceModel entity) {
        if (dto == null) {
            return entity;
        }
        
        entity.setPayerName(dto.getPayerName());
        entity.setPayerCode(dto.getPayerCode());
        
        return entity;
    }

    public InsuranceResponseDTO toDto(InsuranceModel entity) {
        if (entity == null) {
            return null;
        }
        
        return InsuranceResponseDTO.builder()
                .id(entity.getId())
                .payerName(entity.getPayerName())
                .payerCode(entity.getPayerCode())
                .build();
    }
}
