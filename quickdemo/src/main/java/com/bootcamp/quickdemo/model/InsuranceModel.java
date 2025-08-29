package com.bootcamp.quickdemo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "insurances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payer_name", nullable = false)
    private String payerName;
    
    @Column(name = "payer_code")
    private String payerCode;
}
