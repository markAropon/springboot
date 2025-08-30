package com.bootcamp.quickdemo;

import com.bootcamp.quickdemo.dto.DoctorRequestDTO;
import com.bootcamp.quickdemo.model.DoctorModel;
import com.bootcamp.quickdemo.repository.DoctorRepository;
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
public class DoctorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private DoctorModel doctor1;
    private DoctorModel doctor2;

    @BeforeEach
    public void setup() {
        doctorRepository.deleteAll();
        doctor1 = DoctorModel.builder().name("Dr. John").specialty("Cardiology").build();
        doctor2 = DoctorModel.builder().name("Dr. Jane").specialty("Neurology").build();
        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);
    }

    @Test
    public void testGetAllDoctors() throws Exception {
        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Dr. John")))
                .andExpect(jsonPath("$[1].specialty", is("Neurology")));
    }

    @Test
    public void testGetDoctorById() throws Exception {
        mockMvc.perform(get("/doctors/{id}", doctor1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dr. John")))
                .andExpect(jsonPath("$.specialty", is("Cardiology")));
    }

    @Test
    public void testGetDoctorById_NotFound() throws Exception {
        mockMvc.perform(get("/doctors/{id}", 9999L))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCreateDoctor() throws Exception {
        DoctorRequestDTO newDoctor = DoctorRequestDTO.builder().name("Dr. Alice").specialty("Pediatrics").build();
        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDoctor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Dr. Alice")))
                .andExpect(jsonPath("$.specialty", is("Pediatrics")));
    }

    @Test
    public void testUpdateDoctor() throws Exception {
        DoctorRequestDTO updateDoctor = DoctorRequestDTO.builder().name("Dr. John Updated").specialty("Dermatology").build();
        mockMvc.perform(put("/doctors/{id}", doctor1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDoctor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dr. John Updated")))
                .andExpect(jsonPath("$.specialty", is("Dermatology")));
    }

    @Test
    public void testUpdateDoctor_NotFound() throws Exception {
        DoctorRequestDTO updateDoctor = DoctorRequestDTO.builder().name("Ghost").specialty("None").build();
        mockMvc.perform(put("/doctors/{id}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDoctor)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testDeleteDoctor() throws Exception {
        mockMvc.perform(delete("/doctors/{id}", doctor1.getId()))
                .andExpect(status().isNoContent());
        Optional<DoctorModel> deletedDoctor = doctorRepository.findById(doctor1.getId());
        assert(deletedDoctor.isEmpty());
    }
}
