package com.bootcamp.quickdemo;
// TODO not working
import com.bootcamp.quickdemo.dto.PatientInsuranceRequestDTO;
import com.bootcamp.quickdemo.model.PatientInsuranceModel;
import com.bootcamp.quickdemo.repository.PatientInsuranceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientInsuranceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientInsuranceRepository patientInsuranceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientInsuranceModel insurance1;

    @BeforeEach
    public void setup() {
        patientInsuranceRepository.deleteAll();
        insurance1 = PatientInsuranceModel.builder().patientId(1L).insuranceId(1L).build();
        insurance1 = patientInsuranceRepository.save(insurance1);
    }

    @Test
    public void testGetPatientInsuranceById() throws Exception {
        mockMvc.perform(get("/patient-insurances/{id}", insurance1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId", is(1)))
                .andExpect(jsonPath("$.insuranceId", is(1)));
    }

    @Test
    public void testCreatePatientInsurance() throws Exception {
        PatientInsuranceRequestDTO newInsurance = PatientInsuranceRequestDTO.builder().patientId(2L).insuranceId(2L).build();
        mockMvc.perform(post("/patient-insurances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInsurance)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patientId", is(2)))
                .andExpect(jsonPath("$.insuranceId", is(2)));
    }

    @Test
    public void testUpdatePatientInsurance() throws Exception {
        PatientInsuranceRequestDTO updateInsurance = PatientInsuranceRequestDTO.builder().patientId(1L).insuranceId(3L).build();
        mockMvc.perform(put("/patient-insurances/{id}", insurance1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateInsurance)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insuranceId", is(3)));
    }

    @Test
    public void testDeletePatientInsurance() throws Exception {
        mockMvc.perform(delete("/patient-insurances/{id}", insurance1.getId()))
                .andExpect(status().isNoContent());
        Optional<PatientInsuranceModel> deletedInsurance = patientInsuranceRepository.findById(insurance1.getId());
        assert(deletedInsurance.isEmpty());
    }
}
