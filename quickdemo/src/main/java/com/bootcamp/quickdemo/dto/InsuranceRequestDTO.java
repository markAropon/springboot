package com.bootcamp.quickdemo.dto;

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
public class InsuranceRequestDTO {
    @NotBlank(message = "Insurance payer name is required")
    @Size(min = 2, max = 100, message = "Payer name must be between 2 and 100 characters")
    private String payerName;
    
    @NotBlank(message = "Insurance payer code is required")
    @Pattern(regexp = "^[A-Z0-9]{3,10}$", message = "Payer code must be 3-10 alphanumeric characters (uppercase)")
    private String payerCode;
}
