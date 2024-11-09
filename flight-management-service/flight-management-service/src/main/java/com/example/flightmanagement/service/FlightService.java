package com.example.flightmanagement.service;

import com.example.flightmanagement.entity.*;
import com.example.flightmanagement.repository.BookingRepository;
import com.example.flightmanagement.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long flightId) {
        return flightRepository.findById(flightId).orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void updateFlight(Long flightId, Flight updatedFlight) {

        Flight flight = getFlightById(flightId);
        flight.setFlightName(updatedFlight.getFlightName());
        flight.setDepartureAirport(updatedFlight.getDepartureAirport());
        flight.setArrivalAirport(updatedFlight.getArrivalAirport());
        flight.setDepartureTime(updatedFlight.getDepartureTime());
        flight.setArrivalTime(updatedFlight.getArrivalTime());
        flight.setEconomySeats(updatedFlight.getEconomySeats());
        flight.setBusinessSeats(updatedFlight.getBusinessSeats());
        flight.setFirstClassSeats(updatedFlight.getFirstClassSeats());
        flight.setEconomyPrice(updatedFlight.getEconomyPrice());
        flight.setBusinessPrice(updatedFlight.getBusinessPrice());
        flight.setFirstClassPrice(updatedFlight.getFirstClassPrice());
        flight.setStatus(updatedFlight.getStatus());
        flightRepository.save(flight);

    }

    public void deleteFlight(Long flightId) {

        flightRepository.deleteById(flightId); }

    public List<Flight> searchFlights(FlightSearchDto searchCriteria) {
        if (searchCriteria.getDepartureTime() == null) {
            return flightRepository.findByAirports(searchCriteria.getDepartureAirport(), searchCriteria.getArrivalAirport());
        } else {
            return flightRepository.findByAirportsAndDateTime(searchCriteria.getDepartureAirport(), searchCriteria.getArrivalAirport(), searchCriteria.getDepartureTime());
        }
    }

    public Booking bookFlight(Booking booking) {
        String userManagementUrl = "http://localhost:8081/usermanagement/" + booking.getUserId();
        UserDto user = restTemplate.getForObject(userManagementUrl, UserDto.class);

        // Fetch flight details from FlightRepository
        Flight flight = flightRepository.findById(booking.getFlight().getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        // Calculate total price
        double totalPrice = booking.getTotalPrice();

        // Create and save the booking entity
//        Booking booking = new Booking();
        booking.setUserId(booking.getUserId());
        booking.setFlight(flight);
        booking.setTime(LocalDateTime.now());
        //booking.setClasses(bookingDto.getClasses());
        booking.setStatus("Booked");
        //booking.setPaymentMode(booking.getPaymentMode());
        //booking.setNoOfTickets(bookingDto.getNoOfTickets());
        booking.setTotalPrice(totalPrice);

        return bookingRepository.save(booking);
    }

    public String updateFlightDetails(Booking booking, String originalClass, int originalSeats) {
        Flight flight = flightRepository.findById(booking.getFlight().getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        switch (booking.getClasses()) {
            case "Economy":
                if((flight.getEconomySeats() - booking.getNoOfTickets()) > 0) {
                    flight.setEconomySeats(flight.getEconomySeats() - booking.getNoOfTickets());
                }
                else{
                    return "economySeat";
                }
                break;
            case "Business":
                if((flight.getBusinessSeats() - booking.getNoOfTickets()) > 0) {
                    flight.setBusinessSeats(flight.getBusinessSeats() - booking.getNoOfTickets());
                }else{
                    return "bussinessSeat";
                }
                break;
            case "FirstClass":
                if((flight.getFirstClassSeats() - booking.getNoOfTickets()) > 0) {
                    flight.setFirstClassSeats(flight.getFirstClassSeats() - booking.getNoOfTickets());
                }else{
                    return "firstClassSeat";
                }
                break;
        }
        flightRepository.save(flight);
        return "success";
    }

    public String upgradeFlightBooking(BookingUpdateRequest updateRequest) {
        Flight flight = flightRepository.findById(updateRequest.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        int oldEconomySeats = flight.getEconomySeats();
        int oldBusinessSeats = flight.getBusinessSeats();
        int oldFirstClassSeats = flight.getFirstClassSeats();


        // Adjust the seat counts based on the class upgrade
        if (updateRequest.getNewClass().equalsIgnoreCase("Economy")){
            flight.setEconomySeats((int) (oldEconomySeats - updateRequest.getNoOfTickets()));
        }else if(updateRequest.getNewClass().equalsIgnoreCase("Business")){
            flight.setBusinessSeats((int) (oldBusinessSeats - updateRequest.getNoOfTickets()));
        }else {
            flight.setFirstClassSeats((int) (oldFirstClassSeats - updateRequest.getNoOfTickets()));
        }

        if (updateRequest.getOldClass().equalsIgnoreCase("Economy")){
            flight.setEconomySeats((int) (oldEconomySeats + updateRequest.getNoOfTickets()));
        }else if(updateRequest.getOldClass().equalsIgnoreCase("Business")){
            flight.setBusinessSeats((int) (oldBusinessSeats + updateRequest.getNoOfTickets()));
        }else {
            flight.setFirstClassSeats((int) (oldFirstClassSeats + updateRequest.getNoOfTickets()));
        }
        // Save updated flight
        flightRepository.save(flight);

        Booking booking = bookingRepository.findById(updateRequest.getBookingId())
                                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setClasses(updateRequest.getNewClass());
        double totalPrice =  0;
        if(updateRequest.getTotalPrice() == null){
            switch (updateRequest.getNewClass()){
                case "Economy":
                    totalPrice = booking.getNoOfTickets() * flight.getEconomyPrice();
                    break;
                case "Business":
                    totalPrice = booking.getNoOfTickets() * flight.getBusinessPrice();
                    break;
                case "FirstClass":
                    totalPrice = booking.getNoOfTickets() * flight.getFirstClassPrice();
                    break;
            }
            booking.setTotalPrice(totalPrice);
        }else {
            booking.setTotalPrice(Double.valueOf(updateRequest.getTotalPrice()));
        }
        bookingRepository.save(booking);

        return "success";
    }

    public List<FlightBookingDto> getFlightsByUserId(Long userId) {
        // Get all bookings for the user
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        // Convert bookings to DTOs containing relevant flight information
        return bookings.stream().map(booking -> {
            FlightBookingDto dto = new FlightBookingDto();
            dto.setBookingId(booking.getBookingId());
            dto.setFlightId(booking.getFlight().getFlightId());
            dto.setFlightName(booking.getFlight().getFlightName());
            dto.setDepartureAirport(booking.getFlight().getDepartureAirport());
            dto.setArrivalAirport(booking.getFlight().getArrivalAirport());
            dto.setDepartureTime(booking.getFlight().getDepartureTime());
            dto.setArrivalTime(booking.getFlight().getArrivalTime());
            dto.setFlightClass(booking.getClasses());
            dto.setNoOfTickets(booking.getNoOfTickets());
            dto.setTotalPrice(booking.getTotalPrice());
            return dto;
        }).collect(Collectors.toList());
    }
}
