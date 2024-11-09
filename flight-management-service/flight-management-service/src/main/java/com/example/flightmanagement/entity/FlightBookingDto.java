package com.example.flightmanagement.entity;

import java.time.LocalDateTime;

public class FlightBookingDto {
    private Long bookingId;
    private Long userId;
    private Long flightId;
    private String flightName;
    private String classes;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String flightClass;
    private int noOfTickets;
    private double totalPrice;
    private String paymentMode;

    // Getters and Setters
    public Long getBookingId() {return bookingId;}
    public void setBookingId(Long bookingId) {this.bookingId = bookingId;}

    public Long getUserId(){
        return userId;
    }
    public void setUserId (Long userId){
        this.userId = userId;
    }

    public Long getFlightId(){
        return flightId;
    }
    public void setFlightId (Long flightId){
        this.flightId = flightId;
    }

    public String getFlightName(){
        return flightName;
    }
    public void setFlightName (String flightName){
        this.flightName = flightName;
    }

    public String getClasses(){
        return classes;
    }
    public void setClasses (String classes){
        this.classes = classes;
    }

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

    public LocalDateTime getArrivalTime(){
        return arrivalTime;
    }
    public void setArrivalTime (LocalDateTime arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public String getFlightClass(){
        return flightClass;
    }
    public void setFlightClass (String flightClass){
        this.flightClass = flightClass;
    }

    public Integer getNoOfTickets(){
        return noOfTickets;
    }
    public void setNoOfTickets (Integer noOfTickets){
        this.noOfTickets = noOfTickets;
    }

    public Double getTotalPrice(){
        return totalPrice;
    }
    public void setTotalPrice (Double totalPrice){
        this.totalPrice = totalPrice;
    }

    public String getPaymentMode(){
        return paymentMode;
    }
    public void setPaymentMode (String paymentMode){
        this.paymentMode = paymentMode;
    }

}
