package com.flightapp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TicketResponse {
	private String pnr;
    private String status;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String airline;
    private String origin;
    private String dest;
    private List<PassengerInfo> passengers;
    public static class PassengerInfo {
    	private String name;
        private String seatNumber;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSeatNumber() {
			return seatNumber;
		}
		public void setSeatNumber(String seatNumber) {
			this.seatNumber = seatNumber;
		}
        
    }
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
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
	public List<PassengerInfo> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<PassengerInfo> passengers) {
		this.passengers = passengers;
	}
    
}
