package com.bootcamp.quickdemo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionResponseDTO {
    private Integer id;
    private Integer patientId;
    private Integer doctorId;
    private Integer purposeId;
    private LocalDateTime admissionDate;
    private LocalDateTime dischargeDate;
    private String status;
    private String roomNumber;
}