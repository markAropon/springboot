package com.bootcamp.quickdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Data transfer object for vital sign creation requests")
public class VitalSignRequestDTO {
    @Schema(description = "ID of the admission this vital sign belongs to", example = "1")
    private Integer admissionId;
    
    @Schema(description = "Date and time when the vital signs were recorded", example = "2023-10-15T14:30:00")
    private LocalDateTime recordedDate;
    
    @Schema(description = "Body temperature in Celsius", example = "37.2")
    private BigDecimal temperature;
    
    @Schema(description = "Blood pressure reading (systolic/diastolic)", example = "120/80")
    private String bloodPressure;
    
    @Schema(description = "Heart rate in beats per minute", example = "72")
    private Integer heartRate;
    
    @Schema(description = "Respiratory rate in breaths per minute", example = "16")
    private Integer respiratoryRate;
    
    @Schema(description = "Blood oxygen saturation percentage", example = "98.5")
    private BigDecimal spo2;
    
    @Schema(description = "Patient weight in kilograms", example = "70.5")
    private BigDecimal weight;
    
    @Schema(description = "Patient height in centimeters", example = "175.0")
    private BigDecimal height;
}
