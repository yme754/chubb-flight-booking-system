package com.flightapp.repository;

import com.flightapp.entity.Booking;
import com.flightapp.entity.Flight;
import com.flightapp.entity.Airline;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BookingRepositoryTest {
    @Autowired
    private BookingRepository repo;
    @Test
    void findByEmail_success() {
        Booking b = new Booking();
        b.setPnr("PNR1");
        b.setEmail("yas@test.com");
        b.setName("Yas");
        b.setBookingDate(LocalDateTime.now());
        repo.save(b);
        List<Booking> list = repo.findByEmail("yas@test.com");
        assertEquals(1, list.size());
    }
    @Test
    void findByPnr_success() {
        Booking b = new Booking();
        b.setPnr("PNR123");
        b.setEmail("a@a.com");
        b.setName("A");
        b.setBookingDate(LocalDateTime.now());
        repo.save(b);
        Booking result = repo.findByPnr("PNR123");
        assertNotNull(result);
    }
}
