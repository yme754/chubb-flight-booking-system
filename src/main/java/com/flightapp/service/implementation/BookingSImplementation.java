package com.flightapp.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.BookingRequest.PassengerDTO;
import com.flightapp.entity.Booking;
import com.flightapp.entity.Flight;
import com.flightapp.entity.Passenger;
import com.flightapp.exception.BadRequestException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.service.BookingService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookingSImplementation implements BookingService{
    private FlightRepository flightRepo;
    private BookingRepository bookingRepo;
    @Transactional
    @Override
    public Booking bookTicket(int flightId, BookingRequest req) {
        Flight flight = flightRepo.findById(flightId)
        		.orElseThrow(() -> new ResourceNotFoundException("Flight not found for ID: " + flightId));
        if (req.getNumberOfSeats() != req.getPassengers().size())
            throw new BadRequestException("numberOfSeats must match passengers size");
        if (flight.getAvailableSeats() < req.getNumberOfSeats())
        	throw new BadRequestException("Selected seats not available");
        flight.setAvailableSeats(flight.getAvailableSeats()- req.getNumberOfSeats());
        flightRepo.save(flight);
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setName(req.getName());
        booking.setEmail(req.getEmail());
        booking.setMealChoice(req.getMealChoice());
        booking.setBookingDate(LocalDateTime.now());
        booking.setDepartureTime(flight.getDepartureTime());
        booking.setArrivalTime(flight.getArrivalTime());
        booking.setStatus("BOOKED");
        booking.setPnr(generatePnr());
        List<Passenger> passengerEntities = new ArrayList<>();
        for (PassengerDTO p : req.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setName(p.getName());
            passenger.setGender(p.getGender());
            passenger.setAge(p.getAge());
            passenger.setSeatNum(p.getSeatNumber());
            passenger.setBooking(booking);
            passengerEntities.add(passenger);
        }
        booking.setPassengers(passengerEntities);
        Booking saved = bookingRepo.save(booking);
        log.debug("Created booking pnr={}", saved.getPnr());
        return saved;
    }
    @Override
    public Booking getTicketByPnr(String pnr) {
        Booking b = bookingRepo.findByPnr(pnr);
        if (b == null) throw new ResourceNotFoundException("Ticket not found for PNR: " + pnr);
        return b;
    }
    @Override
    public List<Booking> getHistoryByEmail(String email) {
    	List<Booking> list = bookingRepo.findByEmail(email);
        if (list == null || list.isEmpty()) 
        	throw new ResourceNotFoundException("No booking history found for email: " + email);
        return list;
    }
    @Transactional
    @Override
    public Booking cancelByPnr(String pnr) {
        Booking booking = bookingRepo.findByPnr(pnr);
        if (booking == null) throw new ResourceNotFoundException("Ticket not found for PNR: " + pnr);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime journey = booking.getDepartureTime();
        if (journey == null) throw new BadRequestException("Journey date not available");
        if (!now.isBefore(journey.minusHours(24))) throw new BadRequestException("Cannot cancel ticket. Journey is within 24 hours.");
        booking.setStatus("CANCELLED");
        Flight flight = booking.getFlight();
        int seatsToRestore = booking.getPassengers() == null ? 0 : booking.getPassengers().size();
        flight.setAvailableSeats(flight.getAvailableSeats() + seatsToRestore);
        flightRepo.save(flight);
        Booking saved = bookingRepo.save(booking);
        log.debug("Cancelled booking pnr={}", saved.getPnr());
        return saved;
    }
    private String generatePnr() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid.substring(0, 8);
    }
}
