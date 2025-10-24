package com.bootcamp.quickdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentAt;
    private String reason;
    private String status;
}
