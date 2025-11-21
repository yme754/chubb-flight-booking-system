package com.flightapp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.TicketResponse;
import com.flightapp.entity.Booking;
import com.flightapp.entity.Flight;
import com.flightapp.entity.Passenger;
import com.flightapp.exception.BadRequestException;
import com.flightapp.repository.BookingRepository;
import com.flightapp.service.BookingService;
import com.flightapp.service.FlightService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/flight")
public class BookingController {
    private final BookingService bookingService;
    private final FlightService flightService;
    private final BookingRepository bookingRepository;
    public BookingController(BookingService bookingService,FlightService flightService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.bookingRepository = bookingRepository;
    }
    @GetMapping("/ticket/{pnr}")
    public TicketResponse getTicket(@PathVariable String pnr) {
        Booking booking = bookingService.getTicketByPnr(pnr);
        return new TicketResponse(booking);
    }
    @GetMapping("/booking/history/{email}")
    public List<Booking> getHistory(@PathVariable String email) {
        return bookingService.getHistoryByEmail(email);
    }
    @DeleteMapping("/booking/cancel/{pnr}")
    public Booking cancelBooking(@PathVariable String pnr) {
        return bookingService.cancelByPnr(pnr);
    }
    @PostMapping("/booking")
    public TicketResponse bookFlight(@RequestBody @Valid BookingRequest req) {
        Booking saved = bookingService.bookTicket(req.getFlightId(), req);
        return new TicketResponse(saved);
    }
	private String generateRandomPNR() {
		return "PNR" + System.currentTimeMillis();
	}
}
