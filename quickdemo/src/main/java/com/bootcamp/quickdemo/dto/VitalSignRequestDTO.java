package com.bootcamp.quickdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VitalSignRequestDTO {
    private Integer admissionId;
    private LocalDateTime recordedDate;
    private BigDecimal temperature;
    private String bloodPressure;
    private Integer heartRate;
    private Integer respiratoryRate;
    private BigDecimal spo2;
    private BigDecimal weight;
    private BigDecimal height;
}
