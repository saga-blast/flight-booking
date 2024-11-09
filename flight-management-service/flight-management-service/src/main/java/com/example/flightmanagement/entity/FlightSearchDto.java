package com.example.flightmanagement.entity;

import java.time.LocalDateTime;

public class FlightSearchDto {
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureTime;
    // Getters and Setters

    public String getDepartureAirport(){
        return departureAirport;
    }
    public void setDepartureAirport (String departureAirport){
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport(){
        return arrivalAirport;
    }
    public void setArrivalAirport (String arrivalAirport){
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDateTime getDepartureTime(){
        return departureTime;
    }
    public void setDepartureTime (LocalDateTime departureTime){
        this.departureTime = departureTime;
    }


}
