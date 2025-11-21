package com.flightapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void searchFlights_shouldReturn400_whenRequestBodyMissing() throws Exception {
        mockMvc.perform(post("/api/v1.0/flight/search")).andExpect(status().isBadRequest());  
    }
}