package com.flightapp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.dto.SearchRequest;
import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.service.FlightService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/flight/airline")
public class AdminController {
    private AirlineRepository airlineRepository;
    private FlightService flightService;
    @Autowired
    public AdminController(AirlineRepository airlineRepository,
                           FlightService flightService) {
        this.airlineRepository = airlineRepository;
        this.flightService = flightService;
    }
    @GetMapping("/inventory")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }
    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestBody SearchRequest req) {
        return flightService.searchFlights(req);
    }
    @GetMapping("/inventory/{id}")
    public Flight getFlightById(@PathVariable int id) {
        return flightService.findById(id);
    }
    @DeleteMapping("/inventory/{id}")
    public String deleteFlight(@PathVariable int id) {
        flightService.deleteFlight(id);
        return "Flight deleted successfully";
    }

    @PostMapping("/inventory/add")
    public Flight addInventory(@RequestBody @Valid FlightRequest req) {
        Airline existing = airlineRepository.findByAirlineName(req.getAirlineName());
        Airline airline;
        if (existing == null){
            airline = new Airline();
            airline.setAirlineName(req.getAirlineName());
            airline.setLogoURL(req.getLogoURL());
            airline = airlineRepository.save(airline);
        } 
        else{
            existing.setLogoURL(req.getLogoURL());
            airline = airlineRepository.save(existing);
        }
        Flight flight = new Flight();
        flight.setAirline(airline);
        flight.setOrigin(req.getOrigin());
        flight.setDest(req.getDest());
        LocalDateTime dep = LocalDateTime.parse(req.getDepartureTime(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime arr = LocalDateTime.parse(req.getArrivalTime(), DateTimeFormatter.ISO_DATE_TIME);

        flight.setDepartureTime(dep);
        flight.setArrivalTime(arr);
        flight.setDuration(req.getDuration());
        flight.setPrice(req.getPrice());
        flight.setTotalSeats(req.getTotalSeats());
        flight.setAvailableSeats(req.getTotalSeats());
        Flight saved = flightService.addFlight(flight);
        log.debug("Inventory added for airline={}, flightId={}", airline.getAirlineName(), saved.getId());
        return saved;
    }
    public static class FlightRequest {
        private String airlineName;
        private String logoURL;
        private String origin;
        private String dest;
        private double price;
        private int totalSeats;
        private String departureTime;
        private String arrivalTime;
        private String duration;
		public String getAirlineName() {
			return airlineName;
		}
		public void setAirlineName(String airlineName) {
			this.airlineName = airlineName;
		}
		public String getLogoURL() {
			return logoURL;
		}
		public void setLogoURL(String logoURL) {
			this.logoURL = logoURL;
		}
		public String getOrigin() {
			return origin;
		}
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		public String getDest() {
			return dest;
		}
		public void setDest(String dest) {
			this.dest = dest;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public int getTotalSeats() {
			return totalSeats;
		}
		public void setTotalSeats(int totalSeats) {
			this.totalSeats = totalSeats;
		}
		public String getDepartureTime() {
			return departureTime;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public void setDepartureTime(String departureTime) {
			this.departureTime = departureTime;
		}
		public String getArrivalTime() {
			return arrivalTime;
		}
		public void setArrivalTime(String arrivalTime) {
			this.arrivalTime = arrivalTime;
		}
		
        
    }
}
