package com.bootcamp.quickdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Medical history record for a patient")
public class MedicalHistoryDTO {
    @Schema(description = "Unique identifier of the medical history record", example = "1")
    private Long id;
    
    @Schema(description = "ID of the patient this medical history belongs to", example = "5")
    private Long patientId;
    
    @Schema(description = "Medical condition or diagnosis", example = "Hypertension")
    private String condition;
    
    @Schema(description = "ICD-10 diagnosis code", example = "I10")
    private String icd10Code;
    
    @Schema(description = "Date when the condition was diagnosed", example = "2025-09-15")
    private LocalDate diagnosedDate;
    
    @Schema(description = "Current status of the condition", example = "Active")
    private String status;
}

