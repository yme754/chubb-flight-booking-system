package com.flightapp.dto;

import jakarta.validation.constraints.NotBlank;

public class SearchRequest {
	@NotBlank
    private String origin;
    @NotBlank
    private String dest;
    @NotBlank
    private String departureTime;
    @NotBlank
    private String tripType;
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
	
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
    
}
