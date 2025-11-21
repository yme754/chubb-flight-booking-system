package com.flightapp.controller;

import com.flightapp.entity.Flight;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.service.FlightService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
class FlightControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FlightService flightService;
    @Test
    void searchFlights_success() throws Exception {
        when(flightService.searchFlights(any())).thenReturn(List.of(new Flight()));
        mockMvc.perform(post("/api/v1.0/flight/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "origin": "HYD",
                          "dest": "DEL",
                          "departureTime": "2025-11-01T10:00:00",
                          "tripType": "ONEWAY"
                        }
                        """)).andExpect(status().isOk());
    }
    @Test
    void searchFlights_notFound() throws Exception {
        when(flightService.searchFlights(any())).thenThrow(new ResourceNotFoundException("No flights"));
        mockMvc.perform(post("/api/v1.0/flight/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "origin": "HYD",
                                  "dest": "DEL",
                                  "departureTime": "2025-11-01T10:00:00",
                                  "tripType": "ONEWAY"
                                }
                                """))
                .andExpect(status().isNotFound());
    }
    @Test
    void searchFlights_validationFail() throws Exception {
        mockMvc.perform(post("/api/v1.0/flight/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "origin": "",
                                  "dest": "",
                                  "departureTime": "",
                                  "tripType": ""
                                }
                                """)).andExpect(status().isBadRequest());
    }
}
