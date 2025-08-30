package com.bootcamp.quickdemo;

import com.bootcamp.quickdemo.dto.InsuranceRequestDTO;
import com.bootcamp.quickdemo.model.InsuranceModel;
import com.bootcamp.quickdemo.repository.InsuranceRepository;
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
public class InsuranceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private InsuranceModel insurance1;
    private InsuranceModel insurance2;

    @BeforeEach
    public void setup() {
        insuranceRepository.deleteAll();
        insurance1 = InsuranceModel.builder().payerName("Insurance One").build();
        insurance2 = InsuranceModel.builder().payerName("Insurance Two").build();
        insurance1 = insuranceRepository.save(insurance1);
        insurance2 = insuranceRepository.save(insurance2);
    }

    @Test
    public void testGetAllInsurances() throws Exception {
        mockMvc.perform(get("/insurances"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].payerName", is("Insurance One")))
                .andExpect(jsonPath("$[1].payerName", is("Insurance Two")));
    }

    @Test
    public void testGetInsuranceById() throws Exception {
        mockMvc.perform(get("/insurances/{id}", insurance1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payerName", is("Insurance One")));
    }

    @Test
    public void testCreateInsurance() throws Exception {
        InsuranceRequestDTO newInsurance = InsuranceRequestDTO.builder().payerName("Insurance Three").build();
        mockMvc.perform(post("/insurances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInsurance)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.payerName", is("Insurance Three")));
    }

    @Test
    public void testUpdateInsurance() throws Exception {
        InsuranceRequestDTO updateInsurance = InsuranceRequestDTO.builder().payerName("Insurance Updated").build();
        mockMvc.perform(put("/insurances/{id}", insurance1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateInsurance)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payerName", is("Insurance Updated")));
    }

    @Test
    public void testDeleteInsurance() throws Exception {
        mockMvc.perform(delete("/insurances/{id}", insurance1.getId()))
                .andExpect(status().isNoContent());
        Optional<InsuranceModel> deletedInsurance = insuranceRepository.findById(insurance1.getId());
        assert(deletedInsurance.isEmpty());
    }
}
