package com.flightapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
	Booking findByPnr(String pnr);
    List<Booking> findByEmail(String email);
}
