package com.flightapp.service;

import java.util.List;
import com.flightapp.dto.SearchRequest;
import com.flightapp.entity.Flight;

public interface FlightService {
    Flight addFlight(Flight flight);
    List<Flight> searchFlights(SearchRequest req);
    List<Flight> getAllFlights();
    Flight findById(int id);
    void deleteFlight(int id);
	Flight getFlightById(int id);
}
