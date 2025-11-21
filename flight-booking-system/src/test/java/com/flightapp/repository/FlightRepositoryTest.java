package com.flightapp.repository;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FlightRepositoryTest {
    @Autowired
    private FlightRepository repo;
    @Autowired
    private AirlineRepository airlineRepository;
    @Test
    void searchFlights_success() {
        Airline a = new Airline();
        a.setAirlineName("Test");
        a = airlineRepository.save(a);
        Flight f = new Flight();
        f.setAirline(a);
        f.setOrigin("HYD");
        f.setDest("DEL");
        f.setAvailableSeats(10);
        f.setTotalSeats(10);
        f.setPrice(5000);
        f.setDuration("2h");
        f.setDepartureTime(LocalDateTime.now());
        f.setArrivalTime(LocalDateTime.now().plusHours(2));
        repo.save(f);
        List<Flight> result = repo.findByOriginAndDestAndDepartureTimeBetween(
                "HYD", "DEL",
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(5));
        assertEquals(1, result.size());
    }
}
