package com.flightapp.service;

import java.util.List;

import com.flightapp.dto.BookingRequest;
import com.flightapp.entity.Booking;

public interface BookingService {
	public Booking bookTicket(int flightId, BookingRequest req);
    public Booking getTicketByPnr(String pnr);
    public List<Booking> getHistoryByEmail(String email);
    public Booking cancelByPnr(String pnr);
}
