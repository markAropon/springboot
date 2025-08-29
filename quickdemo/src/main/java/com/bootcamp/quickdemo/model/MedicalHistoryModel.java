package com.bootcamp.quickdemo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "medical_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String condition;

    @Column(name = "icd10_code")
    private String icd10Code;

    @Column(name = "diagnosed_date")
    private LocalDate diagnosedDate;

    @Column
    private String status;
}

