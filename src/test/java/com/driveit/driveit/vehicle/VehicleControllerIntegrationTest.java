package com.driveit.driveit.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    @Transactional
    public void whenPostRequestToVehiclesAndValidVehicle_thenCorrectResponse() throws Exception {
        // Given
        String vehicleJson = "{\"registration\":\"AB-124-CD\",\"numberOfSeats\":5,\"service\":1,\"url\":\"http://image-du-webs.com/\",\"emission\":5.12,\"status\":\"UNAVAILABLE\",\"motorization\":{\"name\":\"Electrique\"},\"model\":{\"name\":\"S\",\"brand\":{\"name\":\"Tesla\"}},\"category\":{\"name\":\"SUV\"}}";

        // When
        mockMvc.perform(post("/api/vehicule/service")
                        .contentType("application/json")
                        .content(vehicleJson))
                .andExpect(status().isOk());

        // Then
        Vehicle savedVehicle = vehicleRepository.findByRegistration("AB-124-CD");
        assertThat(savedVehicle).isNotNull();
        assertThat(savedVehicle.getService()).isTrue(); // Vérifie que le champ 'isService' est bien 'true'
    }
}
