package com.bootcamp.quickdemo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "patient_insurances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientInsuranceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "insurance_id", nullable = false)
    private Long insuranceId;

    @Column(name = "policy_number", nullable = false)
    private String policyNumber;

    @Column(name = "coverage_start")
    private LocalDate coverageStart;

    @Column(name = "coverage_end")
    private LocalDate coverageEnd;

    @Column(name = "is_primary")
    private Boolean isPrimary;
}
