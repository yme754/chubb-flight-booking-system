package com.flightapp.service;

import java.util.List;

import com.flightapp.dto.SearchRequest;
import com.flightapp.entity.Flight;

public interface FlightService {
	public Flight addFlight(Flight flight);
    public List<Flight>searchFlights(SearchRequest req);
    public List<Flight> getAllFlights();
    public Flight findById(int id);
}
