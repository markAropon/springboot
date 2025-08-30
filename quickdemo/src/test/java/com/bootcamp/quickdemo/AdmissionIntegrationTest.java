package com.bootcamp.quickdemo;

import com.bootcamp.quickdemo.dto.AdmissionRequestDTO;
import com.bootcamp.quickdemo.model.AdmissionModel;
import com.bootcamp.quickdemo.repository.AdmissionRepository;
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
public class AdmissionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AdmissionModel admission1;
    private AdmissionModel admission2;

    @BeforeEach
    public void setup() {
        admissionRepository.deleteAll();
        admission1 = AdmissionModel.builder().status("status").build();
        admission2 = AdmissionModel.builder().status("Surgery").build();
        admission1 = admissionRepository.save(admission1);
        admission2 = admissionRepository.save(admission2);
    }

    @Test
    public void testGetAllAdmissions() throws Exception {
        mockMvc.perform(get("/admissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status", is("status")))
                .andExpect(jsonPath("$[1].status", is("Surgery")));
    }

    @Test
    public void testGetAdmissionById() throws Exception {
        mockMvc.perform(get("/admissions/{id}", admission1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("status")));
    }

    @Test
    public void testCreateAdmission() throws Exception {
        AdmissionRequestDTO newAdmission = AdmissionRequestDTO.builder().status("Emergency").build();
        mockMvc.perform(post("/admissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAdmission)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("Emergency")));
    }

    @Test
    public void testUpdateAdmission() throws Exception {
        AdmissionRequestDTO updateAdmission = AdmissionRequestDTO.builder().status("Updated status").build();
        mockMvc.perform(put("/admissions/{id}", admission1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAdmission)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Updated status")));
    }

    @Test
    public void testDeleteAdmission() throws Exception {
        mockMvc.perform(delete("/admissions/{id}", admission1.getId()))
                .andExpect(status().isNoContent());
        Optional<AdmissionModel> deletedAdmission = admissionRepository.findById(admission1.getId());
        assert(deletedAdmission.isEmpty());
    }
}
