package com.flightapp.service.implementation;

import com.flightapp.dto.BookingRequest;
import com.flightapp.entity.Booking;
import com.flightapp.entity.Flight;
import com.flightapp.exception.BadRequestException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingSImplementationTest {
    @Mock
    private FlightRepository flightRepo;
    @Mock
    private BookingRepository bookingRepo;
    @InjectMocks
    private BookingSImplementation bookingService;
    private BookingRequest createRequest() {
        BookingRequest req = new BookingRequest();
        req.setFlightId(1);
        req.setName("Yaswitha");
        req.setEmail("yas@gmail.com");
        req.setMealChoice("VEG");
        req.setNumberOfSeats(1);
        BookingRequest.PassengerDTO p = new BookingRequest.PassengerDTO();
        p.setName("Test");
        p.setGender("F");
        p.setAge(20);
        p.setSeatNumber("1A");
        req.setPassengers(List.of(p));
        return req;
    }
    @Test
    void bookTicket_success() {
        Flight f = new Flight();
        f.setId(1);
        f.setAvailableSeats(5);
        f.setDepartureTime(LocalDateTime.now().plusDays(2));
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        when(bookingRepo.save(any())).thenReturn(new Booking());
        Booking result = bookingService.bookTicket(1, createRequest());
        assertNotNull(result);
        verify(bookingRepo, times(1)).save(any());
    }
    @Test
    void bookTicket_flightNotFound() {
        when(flightRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.bookTicket(1, createRequest()));
    }
    @Test
    void bookTicket_seatCountMismatch() {
        BookingRequest req = createRequest();
        req.setNumberOfSeats(2);
        Flight f = new Flight();
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        assertThrows(BadRequestException.class,
                () -> bookingService.bookTicket(1, req));
    }
    @Test
    void bookTicket_notEnoughSeats() {
        Flight f = new Flight();
        f.setAvailableSeats(0);
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        BookingRequest req = createRequest();
        assertThrows(BadRequestException.class,
                () -> bookingService.bookTicket(1, req));
    }
}
