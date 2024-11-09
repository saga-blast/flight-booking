package com.example.flightmanagement.entity;

public class BookingUpdateRequest {
    private Long bookingId;
    private Long userId;
    private Long flightId; // ID of the flight being upgraded
    private Long noOfTickets;
    private String oldClass;
    private String newClass;
    private Long totalPrice;

    // Getter and Setter for userId
    public Long getBookingId() {return bookingId;}
    public void setBookingId(Long bookingId) {this.bookingId = bookingId;}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and Setter for flightId
    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    // Getter and Setter for noOfTickets
    public Long getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(Long noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    // Getter and Setter for oldClass
    public String getOldClass() {
        return oldClass;
    }

    public void setOldClass(String oldClass) {
        this.oldClass = oldClass;
    }

    // Getter and Setter for newClass
    public String getNewClass() {
        return newClass;
    }

    public void setNewClass(String newClass) {
        this.newClass = newClass;
    }

    public Long getTotalPrice() {return totalPrice;}
    public void setTotalPrice(Long totalPrice) {this.totalPrice = totalPrice;}
}

