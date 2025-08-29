package com.bootcamp.quickdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistoryDTO {
    private Long id;
    private Long patientId;
    private String condition;
    private String icd10Code;
    private LocalDate diagnosedDate;
    private String status;
}

