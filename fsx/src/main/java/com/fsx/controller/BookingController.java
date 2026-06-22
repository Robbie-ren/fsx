package com.fsx.controller;


import com.fsx.dto.*;
import com.fsx.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/booking")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/add")
    public BookingAddRespDto addBooking(@Valid @RequestBody BookingReqDto dto, Principal principal){
        String loggedInUsername = principal.getName();
        return bookingService.addBooking(dto, loggedInUsername);
    }

    @GetMapping("/get-one/{id}")
    public BookingDto getById(@PathVariable int id, Principal principal){
        String loggedInUserName = principal.getName();
        return bookingService.getById(id, loggedInUserName);
    }

    //get the tickets that are already booked
    @GetMapping("/bookedSeats/{scheduleId}")
    public List<Integer> getBookedSeats(@PathVariable int scheduleId){
        return bookingService.getBookedSeats(scheduleId);
    }

    //get all bookings by passenger
    @GetMapping("/getAllByPassenger")
    public PaginatedBookingRespDto getAllBookings(Principal principal, @RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size){
        String loggedInUsername = principal.getName();
        return bookingService.getAll(loggedInUsername, page, size);
    }


    @GetMapping("/stat/bookings-by-month")
    public BookingMonthStatRespDto bookingByMonthStat(){
        return bookingService.bookingByMonthStat();
    }

    @PostMapping("/process-booking/{bookingId}")
    public String processBooking(@PathVariable int bookingId, Principal principal) {
        String loggedInUsername = principal.getName();
        return bookingService.processBooking(bookingId, loggedInUsername);
    }

    @GetMapping("/get-bookings")
    public PaginatedBookingRespDto getAllBookings(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size, String keyword){
        return bookingService.getAllBookings(page, size, keyword);
    }

    @PostMapping("/cancelBooking/{requestId}")
    public String cancelBooking(@PathVariable int requestId){
        return bookingService.cancelBooking(requestId);
    }


    @GetMapping("/get-bookings/v2")
    public PaginatedBookingRespDto getAllBookings(Principal principal, @RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size, @RequestParam(required = false)String keyword){
        String username = principal.getName();
        return bookingService.getAllBookingsForOperator(username, page, size, keyword);
    }


}
