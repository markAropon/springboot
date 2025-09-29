package com.bootcamp.quickdemo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientInsuranceRequestDTO {
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    @NotNull(message = "Insurance ID is required")
    private Long insuranceId;
    
    @NotBlank(message = "Policy number is required")
    private String policyNumber;
    
    @NotNull(message = "Coverage start date is required")
    private LocalDate coverageStart;
    
    @FutureOrPresent(message = "Coverage end date must be in the present or future")
    private LocalDate coverageEnd;
    
    private Boolean isPrimary;
}
