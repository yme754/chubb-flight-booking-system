package com.flightapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.dto.SearchRequest;
import com.flightapp.entity.Flight;
import com.flightapp.service.FlightService;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/flight")
public class FlightController {
    private FlightService flightService;
    @PostMapping("/search")
    public List<Flight> searchFlights(@RequestBody @Valid SearchRequest req) {
        log.debug("Searching flights {} -> {} on {}", req.getOrigin(), req.getDest(), req.getDepartureTime());
        return flightService.searchFlights(req);
    }
}
