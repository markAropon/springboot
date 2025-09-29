package com.bootcamp.quickdemo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionRequestDTO {

    private Integer id;
    
    @NotNull(message = "Patient ID is required")
    private Integer patientId;

    @NotNull(message = "Doctor ID is required")
    private Integer doctorId;

    private Integer purposeId;

    @NotNull(message = "Admission date is required")
    @PastOrPresent(message = "Admission date cannot be in the future")
    private LocalDateTime admissionDate;

    private LocalDateTime dischargeDate;

    @NotNull(message = "Status is required")
    @Pattern(regexp = "^(ADMITTED|DISCHARGED|TRANSFERRED)$", message = "Status must be ADMITTED, DISCHARGED, or TRANSFERRED")
    private String status;

    @Size(min = 1, max = 10, message = "Room number must be between 1 and 10 characters")
    private String roomNumber;
}
