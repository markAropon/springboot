package com.bootcamp.quickdemo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// not working 
import com.bootcamp.quickdemo.dto.PatientRequestDTO;
import com.bootcamp.quickdemo.model.PatientModel;
import com.bootcamp.quickdemo.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientModel patient1;
    private PatientModel patient2;

    @BeforeEach
    public void setup() {
        patientRepository.deleteAll();
        patient1 = PatientModel.builder().name("Patient One").build();
        patient2 = PatientModel.builder().name("Patient Two").build();
        patient1 = patientRepository.save(patient1);
        patient2 = patientRepository.save(patient2);
    }

    @Test
    public void testGetAllPatients() throws Exception {
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Patient One")))
                .andExpect(jsonPath("$[1].age", is(40)));
    }

    @Test
    public void testGetPatientById() throws Exception {
        mockMvc.perform(get("/patients/{id}", patient1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Patient One")))
                .andExpect(jsonPath("$.age", is(30)));
    }

    @Test
    public void testCreatePatient() throws Exception {
        PatientRequestDTO newPatient = PatientRequestDTO.builder().name("Patient Three").build();
        mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPatient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Patient Three")))
                .andExpect(jsonPath("$.age", is(25)));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        PatientRequestDTO updatePatient = PatientRequestDTO.builder().name("Patient Updated").build();
        mockMvc.perform(put("/patients/{id}", patient1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Patient Updated")))
                .andExpect(jsonPath("$.age", is(35)));
    }

    @Test
    public void testDeletePatient() throws Exception {
        mockMvc.perform(delete("/patients/{id}", patient1.getId()))
                .andExpect(status().isNoContent());
        Optional<PatientModel> deletedPatient = patientRepository.findById(patient1.getId());
        assert (deletedPatient.isEmpty());
    }
}
