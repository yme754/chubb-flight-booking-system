package com.flightapp.entity;

import java.time.LocalDateTime;

import com.flightapp.validation.ValidArrivalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidArrivalTime
@Entity
public class Flight {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
    @JoinColumn(name = "airline_id")
	private Airline airline;
	@NotBlank
	private String origin;
	@NotBlank
	private String dest;
	@Min(0)
	private double price;
	@Min(1)
	private int totalSeats;
	private int availableSeats;
	@NotNull
	private LocalDateTime departureTime;
	@NotNull
	private LocalDateTime arrivalTime;
	@NotBlank
	private String duration;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
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
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
	public void setFromLoc(String string) {
	}
	public void setToLoc(String string) {		
	}
}
