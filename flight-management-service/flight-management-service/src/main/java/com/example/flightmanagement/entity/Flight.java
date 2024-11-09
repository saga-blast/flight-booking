package com.example.flightmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    private String flightName;
    private String departureAirport;
    private String arrivalAirport;
private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int economySeats;
    private int businessSeats;
    private int firstClassSeats;
    private double economyPrice;
    private double businessPrice;
    private double firstClassPrice;
    private String status;


    // Getters and Setters
    public Long getFlightId(){
        return flightId;
    }
    public void setFlightId(Long flightId){
        this.flightId = flightId;
    }

    public String getFlightNumber(){
        return flightName;
    }
    public void setFlightName(String flightName){
        this.flightName = flightName;
    }

    public String getDepartureAirport(){
        return departureAirport;
    }
    public void setDepartureAirport(String departureAirport){
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport(){
        return arrivalAirport;
    }
    public void setArrivalAirport(String arrivalAirport){
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDateTime getDepartureTime(){
        return departureTime;
    }
    public void setDepartureTime(LocalDateTime departureTime){
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime(){
        return arrivalTime;
    }
    public void setArrivalTime(LocalDateTime arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public Integer getEconomySeats(){
        return economySeats;
    }
    public void setEconomySeats (Integer economySeats){
        this.economySeats = economySeats;
    }

    public Integer getBusinessSeats(){
        return businessSeats;
    }
    public void setBusinessSeats (Integer businessSeats){
        this.businessSeats = businessSeats;
    }

    public Integer getFirstClassSeats(){
        return firstClassSeats;
    }
    public void setFirstClassSeats (Integer firstClassSeats){
        this.firstClassSeats = firstClassSeats;
    }

    public Double getEconomyPrice(){
        return economyPrice;
    }
    public void setEconomyPrice (Double economyPrice){
        this.economyPrice = economyPrice;
    }

    public Double getBusinessPrice(){
        return businessPrice;
    }
    public void setBusinessPrice (Double businessPrice){
        this.businessPrice = businessPrice;
    }

    public Double getFirstClassPrice(){
        return firstClassPrice;
    }
    public void setFirstClassPrice (Double firstClassPrice){
        this.firstClassPrice = firstClassPrice;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus (String status){
        this.status = status;
    }

}
