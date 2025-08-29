package com.bootcamp.quickdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceResponseDTO {
    private Long id;
    private String payerName;
    private String payerCode;
}
