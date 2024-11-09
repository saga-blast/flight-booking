package com.example.flightmanagement.entity;


public class BookingDto {
    private Long userId;
    private Long flightId;
    private String classes;
    private String paymentMode;
    private int noOfTickets;
    // Getters and Setters

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

    public String getClasses(){
        return classes;
    }
    public void setClasses (String classes){
        this.classes = classes;
    }

    public String getPaymentMode(){
        return paymentMode;
    }
    public void setPaymentMode (String paymentMode){
        this.paymentMode = paymentMode;
    }

    public Integer getNoOfTickets(){
        return noOfTickets;
    }
    public void setNoOfTickets (Integer noOfTickets){
        this.noOfTickets = noOfTickets;
    }
}
