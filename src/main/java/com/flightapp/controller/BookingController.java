package com.flightapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.TicketResponse;
import com.flightapp.entity.Booking;
import com.flightapp.entity.Passenger;
import com.flightapp.service.BookingService;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/flight")
public class BookingController {
    private BookingService bookingService;
    @PostMapping("/booking/{flightId}")
    public Booking createBooking(@PathVariable int flightId, @RequestBody @Valid BookingRequest req) {
        log.debug("Booking request for flightId={}, seats={}", flightId, req.getNumberOfSeats());
        return bookingService.bookTicket(flightId, req);
    }
    @GetMapping("/ticket/{pnr}")
    public TicketResponse getTicket(@PathVariable String pnr) {
        Booking booking = bookingService.getTicketByPnr(pnr);
        TicketResponse res = new TicketResponse();
        res.setPnr(booking.getPnr());
        res.setStatus(booking.getStatus());
        res.setDepartureTime(booking.getDepartureTime());
        res.setAirline(booking.getFlight().getAirline().getAirlineName());
        res.setOrigin(booking.getFlight().getOrigin());
        res.setDest(booking.getFlight().getDest());
        List<TicketResponse.PassengerInfo> list = new ArrayList<>();
        if (booking.getPassengers() != null) {
            for (Passenger p : booking.getPassengers()) {
                TicketResponse.PassengerInfo pi = new TicketResponse.PassengerInfo();
                pi.setName(p.getName());
                pi.setSeatNumber(p.getSeatNum());
                list.add(pi);
            }
        }
        res.setPassengers(list);
        return res;
    }
    @GetMapping("/booking/history/{email}")
    public List<Booking> getHistory(@PathVariable String email) {
        return bookingService.getHistoryByEmail(email);
    }
    @DeleteMapping("/booking/cancel/{pnr}")
    public Booking cancelBooking(@PathVariable String pnr) {
        return bookingService.cancelByPnr(pnr);
    }
}
