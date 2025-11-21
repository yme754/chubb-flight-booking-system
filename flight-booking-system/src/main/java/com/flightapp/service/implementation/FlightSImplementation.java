package com.flightapp.service.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.dto.SearchRequest;
import com.flightapp.entity.Flight;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.repository.FlightRepository;
import com.flightapp.service.FlightService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlightSImplementation implements FlightService {

    private final FlightRepository flightRepo;
    @Autowired
    public FlightSImplementation(FlightRepository flightRepo) {
        this.flightRepo = flightRepo;
    }
    @Override
    public List<Flight> getAllFlights() {
        List<Flight> list = flightRepo.findAll();
        if (list == null || list.isEmpty()) {
            throw new ResourceNotFoundException("No flights found");
        }
        return list;
    }

    public Flight addFlight(Flight flight) {
        if (flight.getAvailableSeats() == 0) {
            flight.setAvailableSeats(flight.getTotalSeats());
        }
        Flight saved = flightRepo.save(flight);
        log.debug("Added flight: {}", saved.getId());
        return saved;
    }
    @Override
    public List<Flight> searchFlights(SearchRequest req) {
        LocalDateTime dt = LocalDateTime.parse(req.getDepartureTime(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime start = dt.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = dt.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
        List<Flight> results =
                flightRepo.findByOriginAndDestAndDepartureTimeBetween(
                        req.getOrigin(), req.getDest(), start, end);
        log.debug("Search returned {} flights", results.size());
        if (results == null || results.isEmpty()) {
            throw new ResourceNotFoundException("No flights found for given criteria");
        }
        return results;
    }
    @Override
    public Flight findById(int id) {
        return flightRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found for ID: " + id));
    }
    @Override
    public void deleteFlight(int id) {
        if (!flightRepo.existsById(id)) {
            throw new ResourceNotFoundException("Flight not found: " + id);
        }
        flightRepo.deleteById(id);
    }
    @Override
    public Flight getFlightById(int id) {
        return flightRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + id));
    }
}
