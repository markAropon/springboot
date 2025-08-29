package com.bootcamp.quickdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientInsuranceResponseDTO {
    private Long id;
    private Long patientId;
    private Long insuranceId;
    private String policyNumber;
    private LocalDate coverageStart;
    private LocalDate coverageEnd;
    private Boolean isPrimary;
    private InsuranceResponseDTO insurance;
}
