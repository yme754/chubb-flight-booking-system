package com.flightapp.controller;

import com.flightapp.dto.BookingRequest;
import com.flightapp.entity.Airline;
import com.flightapp.entity.Booking;
import com.flightapp.entity.Flight;
import com.flightapp.entity.Passenger;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.exception.BadRequestException;
import com.flightapp.repository.BookingRepository;
import com.flightapp.service.BookingService;
import com.flightapp.service.FlightService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingService bookingService;
    @MockBean
    private FlightService flightService;
    @MockBean
    private BookingRepository bookingRepository;
    @Test
    void bookFlight_flightNotFound() throws Exception {
    	when(bookingService.bookTicket(anyInt(), any()))
        .thenThrow(new ResourceNotFoundException("Flight not found"));

        mockMvc.perform(post("/api/v1.0/flight/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "flightId": 1,
                      "name": "A",
                      "email": "a@a.com",
                      "mealChoice": "VEG",
                      "numberOfSeats": 1,
                      "passengers": [
                        {"name": "P", "gender": "F", "age": 20, "seatNumber": "1A"}
                      ]
                    }
                """)).andExpect(status().isNotFound());
    }
    @Test
    void getTicket_success() throws Exception {
        Airline airline = new Airline();
        airline.setAirlineName("Indigo");
        Flight flight = new Flight();
        flight.setOrigin("HYD");
        flight.setDest("DEL");
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        flight.setAirline(airline);
        Booking b = new Booking();
        b.setPnr("PNR123");
        b.setFlight(flight);
        b.setPassengers(List.of());
        when(bookingService.getTicketByPnr("PNR123")).thenReturn(b);
        mockMvc.perform(get("/api/v1.0/flight/ticket/PNR123")).andExpect(status().isOk());
    }
    @Test
    void cancelBooking_success() throws Exception {
        Booking b = new Booking();
        b.setPnr("PNR1");
        when(bookingService.cancelByPnr("PNR1")).thenReturn(b);
        mockMvc.perform(delete("/api/v1.0/flight/booking/cancel/PNR1"))
                .andExpect(status().isOk());
    }
}
