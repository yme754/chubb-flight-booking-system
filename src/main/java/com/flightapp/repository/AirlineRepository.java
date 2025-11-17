package com.flightapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.entity.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer>{
	Airline findByAirlineName(String airlineName);
}
