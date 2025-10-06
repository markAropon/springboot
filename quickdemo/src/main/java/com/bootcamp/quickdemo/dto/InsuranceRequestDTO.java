package com.bootcamp.quickdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for insurance creation requests")
public class InsuranceRequestDTO {
    @NotBlank(message = "Insurance payer name is required")
    @Size(min = 2, max = 100, message = "Payer name must be between 2 and 100 characters")
    @Schema(description = "Name of the insurance payer/company", example = "Blue Cross Blue Shield")
    private String payerName;
    
    @NotBlank(message = "Insurance payer code is required")
    @Pattern(regexp = "^[A-Z0-9]{3,10}$", message = "Payer code must be 3-10 alphanumeric characters (uppercase)")
    @Schema(description = "Unique code for the insurance payer (3-10 uppercase alphanumeric characters)", example = "BCBS123")
    private String payerCode;
}
