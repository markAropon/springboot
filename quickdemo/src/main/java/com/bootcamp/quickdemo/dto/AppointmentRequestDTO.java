package com.bootcamp.quickdemo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {

    @NotNull
    // Support both camelCase and snake_case field names
    @JsonProperty(value = "patientId", access = JsonProperty.Access.READ_WRITE)
    private Long patientId;

    @NotNull
    // Support both camelCase and snake_case field names
    @JsonProperty(value = "doctorId", access = JsonProperty.Access.READ_WRITE)
    private Long doctorId;

    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]['Z']", timezone = "UTC")
    private LocalDateTime appointmentAt;

    private String reason;

    private Integer id;
    private String status;
}
