package com.example.flightmanagement.controller;

import com.example.flightmanagement.entity.*;
import com.example.flightmanagement.repository.BookingRepository;
import com.example.flightmanagement.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/flightmanagement")
    public String index() {
        return "/flightmanagement/index";
    }

    @GetMapping("/flightmanagement/addFlight")
    public String addFlightForm(Model model) {
        model.addAttribute("flight",new Flight());
        return "/flightmanagement/addFlight";
    }

    @PostMapping("/flightmanagement/addFlight")
    public String addFlightSubmit(@ModelAttribute("flight") Flight flight) {
        flightService.saveFlight(flight);
        return "redirect:/flightmanagement/viewFlights";
    }

    @GetMapping("/flightmanagement/viewFlights")
    public String viewAllFlights(Model model) {
        System.out.println("before model attribute");
        model.addAttribute("flights", flightService.getAllFlights());
        System.out.println("after model attribute");
        return "/flightmanagement/viewFlights";
    }

    @GetMapping("/flightmanagement/editFlight/{flightId}")
    public String showEditFlightForm(@PathVariable Long flightId, Model model) {
        Flight flight = flightService.getFlightById(flightId);
        model.addAttribute("flight", flight);
        return "/flightmanagement/editFlight";
    }

    @PostMapping("/flightmanagement/editFlight/{flightId}")
    public String updateFlight(@PathVariable Long flightId, @ModelAttribute("flight") Flight flight) {
        flightService.updateFlight (flightId, flight);
        return "redirect:/flightmanagement/viewFlights";
    }

    @GetMapping("/flightmanagement/deleteFlight/{flightId}")
    public String deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return "redirect:/flightmanagement/viewFlights"; }

    @GetMapping("/flightmanagement/search/{userId}")
    public String searchFlights(@RequestParam(required = false) String departureAirport,
                                @RequestParam(required = false) String arrivalAirport,
                                @RequestParam(required = false) LocalDateTime departureTime,
                                @PathVariable Long userId,
                                Model model) {
        FlightSearchDto searchCriteria = new FlightSearchDto();
        searchCriteria.setDepartureAirport(departureAirport);
        searchCriteria.setArrivalAirport(arrivalAirport);
        searchCriteria.setDepartureTime(departureTime);
        if(departureAirport!=null){
            model.addAttribute("departureAirport", departureAirport);
        }
        if(arrivalAirport!=null){
            model.addAttribute("arrivalAirport", arrivalAirport);
        }
        if(departureTime!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String formattedDepartureTime = departureTime.format(formatter);
            model.addAttribute("departureTime", formattedDepartureTime);
        }
        List<Flight> flights = flightService.searchFlights(searchCriteria);
        if(flights.size() == 0 && departureAirport!=null && arrivalAirport!=null){
            model.addAttribute("searchCondition", true);
            model.addAttribute("showAvailableTable", false);
        }else{
            model.addAttribute("searchCondition", false);
            model.addAttribute("showAvailableTable", true);
        }
        if(departureAirport==null && arrivalAirport==null){
            model.addAttribute("showAvailableTable", false);
        }
        model.addAttribute("flights", flights);
        model.addAttribute("userId", userId);
        return "flightmanagement/searchFlight";
    }

    @GetMapping("/flightmanagement/bookFlight/{flightId}")
    public String showBookFlightPage(@RequestParam(required = false) String departureAirport,
                                     @RequestParam(required = false) String arrivalAirport,
                                     @RequestParam(required = false) String departureTime,
                                     @PathVariable Long flightId,
                                     @RequestParam(required = true) Long userId,
                                     Model model) {
        // Add attributes for form binding
        model.addAttribute("departureAirport", departureAirport);
        model.addAttribute("arrivalAirport", arrivalAirport);
        model.addAttribute("departureTime", departureTime);
        model.addAttribute("flightId", flightId);
        Flight flight = flightService.getFlightById(flightId);
        model.addAttribute("tempFlight", flight);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDepartureTime = flight.getDepartureTime().format(formatter); // Pre-format the LocalDateTime
        model.addAttribute("formattedDepartureTime", formattedDepartureTime); // Add formatted time to the model
        Booking booking = new Booking(); // Create a new Booking instance
        model.addAttribute("booking", booking); // Add Booking object to the model
        model.addAttribute("userId", userId);
        return "flightmanagement/bookFlight";  // This will render the bookFlight.html template
    }
    // Book a flight
//    @PostMapping("/flightmanagement/bookFlight/{userId}")
//    public ResponseEntity<String> bookFlight(@PathVariable Long userId,
//                                             FlightBookingDto bookingDto) {
//        // Fetch user details from user management microservice
////        ResponseEntity userResponse = restTemplate.getForEntity("http://localhost:8081/users/" + bookingDto.getUserId(), User.class);
//
//        // Ensure flight exists
////        Flight flight = flightService.getFlightById(bookingDto.getFlightId());
//
//        // Perform booking logic
//        bookingDto.setUserId(userId);
//        Booking booking = flightService.bookFlight(bookingDto);
//
//        return ResponseEntity.ok("Flight booked successfully! Booking ID: " + booking.getBookingId());
//    }

    @GetMapping("/flightmanagement/myFlights/{userId}")
    public String viewMyFlights(@PathVariable Long userId,
                                Model model){
        //Long userId = user.getUserId();
        List<FlightBookingDto> flights = flightService.getFlightsByUserId(userId);
        model.addAttribute("flights",flights);
        model.addAttribute("userId", userId );
        return "flightmanagement/myFlights";
    }

    @GetMapping("/flightmanagement/book/{flightId}")
    public String bookFlight(@PathVariable Long flightId, Model model) {
        Flight flight = flightService.getFlightById(flightId);
        model.addAttribute("flight", flight);
        return "bookFlight";
    }

    @PostMapping("/flightmanagement/book")
    public ResponseEntity<String> bookFlightSubmit(@RequestBody Booking booking, Model model) {

        String updateResult = flightService.updateFlightDetails(booking, booking.getClasses(), booking.getNoOfTickets());

        if ("success".equals(updateResult)) {
            // Success case - call the other service
            flightService.bookFlight(booking);
            String url = "http://localhost:8081/usermanagement/passengerDashboard?userId=" + booking.getUserId();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.ok("Booking successful!"); // Return success message
        } else if("economySeat".equals(updateResult)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Flight update failed.");
        } else if("bussinessSeat".equals(updateResult)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Flight update failed.");
        } else if("firstClassSeat".equals(updateResult)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Flight update failed.");
        } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Flight update failed.");
        }
    }

    @GetMapping("/flightmanagement/upgradeBooking")
    public String upgradeBooking(@RequestParam("flightId") Long flightId,
                                 @RequestParam("userId") Long userId,
                                 Model model) {
        Flight flight = flightService.getFlightById(flightId);
        List<FlightBookingDto> bookingDtos = flightService.getFlightsByUserId(userId);
        for(FlightBookingDto flightBookingDto : bookingDtos) {
            if (flightBookingDto.getFlightId().equals(flightId)) {
                flightBookingDto.setUserId(userId);
                model.addAttribute("booking", flightBookingDto);
                model.addAttribute("flight", flight);
                if(flightBookingDto.getFlightClass().equalsIgnoreCase("Economy")){
                    model.addAttribute("isBussiness", true);
                    model.addAttribute("isFirstClass", true);
                }else if(flightBookingDto.getFlightClass().equalsIgnoreCase("Business")){
                    model.addAttribute("isEconomy", true);
                    model.addAttribute("isFirstClass", true);
                }else{
                    model.addAttribute("isEconomy",true);
                    model.addAttribute("isBussiness", true);
                }
            }
        }

        return "flightmanagement/upgradeBooking";
    }

    @PostMapping("/flightmanagement/upgradeBooking")
    public ResponseEntity<String> upgradeBooking(@RequestBody BookingUpdateRequest bookingUpdateRequest) {

        String result = flightService.upgradeFlightBooking(bookingUpdateRequest);
        if (result.equals("success")) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

}
