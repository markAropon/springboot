package com.bootcamp.quickdemo.dto;


import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionRequestDTO {

    private Integer id;
    
    private Integer patientId;


    private Integer doctorId;


    private Integer purposeId;


    private LocalDateTime admissionDate;

    private LocalDateTime dischargeDate;

    private String status;

    private String roomNumber;
}
