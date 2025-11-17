package com.flightapp;

import com.flightapp.entity.Flight;
import com.flightapp.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlightRepositoryTest {
    @Autowired
    private FlightRepository flightRepository;
    @Test
    void testSaveFlight() {
        Flight f = new Flight();
        f.setOrigin("BLR");
        f.setDest("DEL");
        f.setPrice(5000);
        f.setTotalSeats(180);
        f.setAvailableSeats(180);
        f.setDepartureTime(java.time.LocalDateTime.now());
        f.setArrivalTime(java.time.LocalDateTime.now().plusHours(2));
        f.setDuration("2h");
        flightRepository.save(f);
    }
}