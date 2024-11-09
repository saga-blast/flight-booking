package com.example.flightmanagement.repository;

import com.example.flightmanagement.entity.Booking;
import com.example.flightmanagement.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT f FROM Flight f WHERE f.departureAirport = :departureAirport AND f.arrivalAirport = :arrivalAirport AND f.departureTime >= :departureTime")
    List<Flight> findByAirportsAndDateTime(@Param("departureAirport") String departureAirport,
                                           @Param("arrivalAirport") String arrivalAirport,
                                           @Param("departureTime") LocalDateTime departureTime);

    List<Booking> findByUserId(Long userId);
}