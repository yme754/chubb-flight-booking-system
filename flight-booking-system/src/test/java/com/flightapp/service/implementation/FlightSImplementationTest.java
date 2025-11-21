package com.flightapp.service.implementation;

import com.flightapp.entity.Flight;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.repository.FlightRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FlightSImplementationTest {
    @Mock
    private FlightRepository flightRepo;
    @InjectMocks
    private FlightSImplementation flightService;
    @Test
    void getFlightById_success() {
        Flight f = new Flight();
        f.setId(1);
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        Flight result = flightService.getFlightById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }
    @Test
    void getFlightById_notFound() {
        when(flightRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> flightService.getFlightById(1));
    }
    @Test
    void getAllFlights_success() {
        when(flightRepo.findAll()).thenReturn(List.of(new Flight(), new Flight()));
        List<Flight> list = flightService.getAllFlights();
        assertEquals(2, list.size());
    }
    @Test
    void getAllFlights_empty() {
        when(flightRepo.findAll()).thenReturn(List.of());
        assertThrows(ResourceNotFoundException.class, () -> flightService.getAllFlights());
    }
}
