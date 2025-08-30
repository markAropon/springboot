package com.bootcamp.quickdemo;
// TODO not working
import com.bootcamp.quickdemo.dto.MedicalHistoryDTO;
import com.bootcamp.quickdemo.model.MedicalHistoryModel;
import com.bootcamp.quickdemo.repository.MedicalHistoryRepository;
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
public class MedicalHistoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MedicalHistoryModel history1;

    @BeforeEach
    public void setup() {
        medicalHistoryRepository.deleteAll();
        history1 = MedicalHistoryModel.builder().patientId(1L).condition("Diabetes").build();
        history1 = medicalHistoryRepository.save(history1);
    }

    @Test
    public void testGetAllMedicalHistory() throws Exception {
        mockMvc.perform(get("/medical_history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is("Diabetes")));
    }

    @Test
    public void testGetMedicalHistoryById() throws Exception {
        mockMvc.perform(get("/medical_history/{id}", history1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Diabetes")));
    }

    @Test
    public void testCreateMedicalHistory() throws Exception {
        MedicalHistoryDTO newHistory = MedicalHistoryDTO.builder().patientId(1L).condition("Asthma").build();
        mockMvc.perform(post("/medical_history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newHistory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Asthma")));
    }

    @Test
    public void testUpdateMedicalHistory() throws Exception {
        MedicalHistoryDTO updateHistory = MedicalHistoryDTO.builder().patientId(1L).condition("Updated Desc").build();
        mockMvc.perform(put("/medical_history/{id}", history1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateHistory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Updated Desc")));
    }

    @Test
    public void testDeleteMedicalHistory() throws Exception {
        mockMvc.perform(delete("/medical_history/{id}", history1.getId()))
                .andExpect(status().isNoContent());
        Optional<MedicalHistoryModel> deletedHistory = medicalHistoryRepository.findById(history1.getId());
        assert(deletedHistory.isEmpty());
    }
}
