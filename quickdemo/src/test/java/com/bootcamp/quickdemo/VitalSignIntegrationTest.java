package com.bootcamp.quickdemo;
//TODO NOT WORKING
import com.bootcamp.quickdemo.dto.VitalSignRequestDTO;
import com.bootcamp.quickdemo.model.VitalSignModel;
import com.bootcamp.quickdemo.repository.VitalSignRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VitalSignIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VitalSignRepository vitalSignRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private VitalSignModel vitalSign1;

    @BeforeEach
    public void setup() {
        vitalSignRepository.deleteAll();
    vitalSign1 = VitalSignModel.builder().admissionId(1).bloodPressure("120/80").build();
        vitalSign1 = vitalSignRepository.save(vitalSign1);
    }

    @Test
    public void testCreateVitalSign() throws Exception {
    VitalSignRequestDTO newVitalSign = VitalSignRequestDTO.builder().admissionId(1).temperature(new java.math.BigDecimal("98.6")).build();
        mockMvc.perform(post("/admissions/{admissionId}/vital-signs", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newVitalSign)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.temperature", is(98.6)));
    }

    @Test
    public void testGetVitalSignById() throws Exception {
        mockMvc.perform(get("/vital-signs/{id}", vitalSign1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bloodPressure", is("120/80")));
    }
}
