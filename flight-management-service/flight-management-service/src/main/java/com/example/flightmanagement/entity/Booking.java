package com.example.flightmanagement.entity;


import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "flightId")
    private Flight flight;
    private LocalDateTime time;
    private String classes;
    private String status;
    private String paymentMode;
    private int noOfTickets;
    private double totalPrice;

    // Getters and Setters
    public Long getBookingId(){
        return bookingId;
    }
    public void setBookingId(Long bookingId){

        this.bookingId = bookingId;
    }

    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId){

        this.userId = userId;
    }

    public Flight getFlight(){
        return flight;
    }
    public void setFlight(Flight flight){

        this.flight = flight;
    }

    public LocalDateTime getTime(){

        return time;
    }
    public void setTime(LocalDateTime time){

        this.time = time;
    }

    public String getClasses(){
        return classes;
    }
    public void setClasses (String classes){
        this.classes = classes;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus (String status){
        this.status = status;
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

    public Double getTotalPrice(){
        return totalPrice;
    }
    public void setTotalPrice (Double totalPrice){
        this.totalPrice = totalPrice;
    }
}