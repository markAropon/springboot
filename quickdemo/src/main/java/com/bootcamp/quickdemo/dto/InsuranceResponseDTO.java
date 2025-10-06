package com.bootcamp.quickdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for insurance responses")
public class InsuranceResponseDTO {
    @Schema(description = "Unique identifier of the insurance record", example = "1")
    private Long id;
    
    @Schema(description = "Name of the insurance payer/company", example = "Blue Cross Blue Shield")
    private String payerName;
    
    @Schema(description = "Unique code for the insurance payer", example = "BCBS123")
    private String payerCode;
}
