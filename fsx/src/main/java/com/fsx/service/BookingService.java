package com.fsx.service;

import com.fsx.dto.*;
import com.fsx.enums.*;
import com.fsx.exception.*;
import com.fsx.mapper.TicketMapper;
import com.fsx.model.*;
import com.fsx.repository.BookingRepository;
import com.fsx.mapper.BookingMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private final ScheduleService scheduleService;
    private final TicketService ticketService;
    private final PassengerService passengerService;
    private final TicketMapper ticketMapper;
    private final SeatReservationService seatReservationService;
    private BookingRepository bookingRepository;
    private BookingMapper bookingMapper;
    private final CancellationRequestService cancellationRequestService;

    //Add booking
    public BookingAddRespDto addBooking(BookingReqDto dto, String userName) {
        Passenger passenger = passengerService.getByUserName(userName);
        if(passenger.getStatus()== PassengerStatus.UNVERIFIED){
            throw new InvalidBookingException("You are unverified. Upload ID proof");
        }
        Schedule schedule = scheduleService.getById(dto.scheduleId());
        //check if enough seats are present
        if (schedule.getAvailableSeats() < dto.seatNumbers().size())
            throw new InvalidSeatException("Not enough available seats left on this journey.");
        //Checking if the selected seats are available
        List<BookingStatus> activeStatuses = List.of(BookingStatus.CONFIRMED, BookingStatus.PENDING);
        int duplicateSeatCount = ticketService.countReservedSeats(dto.scheduleId(), dto.seatNumbers(), activeStatuses);
        if (duplicateSeatCount > 0)
            throw new InvalidSeatException("One or more of your selected seats were taken. Please choose different seats.");

        Booking booking = BookingMapper.mapDtoToEntity(dto, schedule, passenger);
        //calculating totalAmount according to price of ticket from schedule
        booking.setTotalAmount(booking.getSchedule().getPrice() * booking.getSeatCount());
        booking.setBookingStatus(BookingStatus.PENDING);
        Optional<Booking> existing = bookingRepository.findByPassengerAndScheduleAndBookingStatus(
                                passenger,
                                schedule,
                                BookingStatus.PENDING
                        );
        if(existing.isPresent()){
            return new BookingAddRespDto(existing.get().getId());
        }
        // add booking
        bookingRepository.save(booking);
        //adding tickets in the tickets table
        //ticketService.addTicket(booking, dto.seatNumbers());
        seatReservationService.addReservation(booking, dto.seatNumbers());
        int bookingId = booking.getId();
        return new BookingAddRespDto(bookingId);
    }



    //get booking by booking id(including tickets)
    public BookingDto getById(int id, String loggedInUserName) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid id..."));
        if(!booking.getPassenger().getUser().getUsername().equals(loggedInUserName)){
            throw new InvalidOwnershipException("You do not own this booking...");
        }
        return BookingMapper.mapEntityToDto(booking);
    }



    //get all bookings by passenger
    public PaginatedBookingRespDto getAll(String loggedInUserName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookings = bookingRepository.findByUsername(loggedInUserName, pageable);
        List<BookingDto> list = bookings.getContent()
                .stream()
                .map(BookingMapper::mapEntityToDto)
                .toList();
        return new PaginatedBookingRespDto(bookings.getTotalElements(), bookings.getTotalPages(), list);
    }

    //get all bookings
    public PaginatedBookingRespDto getAllBookings(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookings = bookingRepository.search(keyword, pageable);
        List<BookingDto> list = bookings.getContent()
                .stream()
                .map(BookingMapper::mapEntityToDto)
                .toList();
        return new PaginatedBookingRespDto(bookings.getTotalElements(), bookings.getTotalPages(), list);
    }

    public PaginatedBookingRespDto getAllBookingsForOperator(String username, int page, int size, String keyword){
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookings = bookingRepository.searchBookings(username, keyword, pageable);
        List<BookingDto> list = bookings.getContent()
                .stream()
                .map(BookingMapper::mapEntityToDto)
                .toList();
        return new PaginatedBookingRespDto(bookings.getTotalElements(), bookings.getTotalPages(), list);
    }



    public Booking findById(int bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Invalid booking id"));
    }



    public void save(Booking booking) {
        bookingRepository.save(booking);
    }




    public List<Integer> getBookedSeats(int scheduleId) {

        return ticketService.getBookedSeats(scheduleId);
    }

    public void delete(int id){
        findById(id);
        bookingRepository.deleteById(id);
    }

    public BookingMonthStatRespDto bookingByMonthStat() {
        List<BookingMonthStatJpqlRespDto> list = bookingRepository.getNumberOfBookingsByMonth();
        List<Integer> months = list.stream().map(BookingMonthStatJpqlRespDto :: month).toList();
        List<Long> numberOfBooking = list.stream().map(BookingMonthStatJpqlRespDto::numberOfBookings).toList();
        return new BookingMonthStatRespDto("Bookings By Month", months, numberOfBooking);
    }

    public String processBooking(int bookingId, String loggedInUsername) {
        Booking booking = findById(bookingId);
        if(booking == null)
            throw new ResourceNotFoundException("Booking not found");
        if(!booking.getPassenger().getUser().getUsername().equals(loggedInUsername))
            throw new InvalidOwnershipException("You cannot make this booking. You are unauthorized");
        if(booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new InvalidBookingException("Booking cannot be processed");
        }
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        int scheduleId = booking.getSchedule().getId();
        int seatCount = booking.getSeatCount();
        scheduleService.decrementAvailableSeats(scheduleId, seatCount);
        List<SeatReservation> seatReservation = seatReservationService.getReservation(bookingId);
        seatReservation.forEach(s->{
            Ticket ticket = new Ticket();
            ticket.setBooking(booking);
            ticket.setSeatNumber(s.getSeatNumber());
            ticket.setPassengerName(s.getPassengerName());
            ticket.setPassengerAge(s.getPassengerAge());
            ticket.setStatus(TicketStatus.CONFIRMED);
            ticketService.saveTicket(ticket);
        });



        return "Booking successful! Booking is confirmed.";
    }

    public String cancelBooking(int requestId) {

        CancellationRequest cancellationRequest = cancellationRequestService.getById(requestId);
        Booking booking = cancellationRequest.getBooking();
        int bookingId = booking.getId();

        Schedule schedule = booking.getSchedule();
        List<Ticket> tickets = ticketService.getTicketsByBookingId(bookingId);
        for(Ticket ticket : tickets){
            ticket.setStatus(TicketStatus.CANCELLED);
        }
        int seatCount = booking.getSeatCount();
        schedule.setAvailableSeats(schedule.getAvailableSeats() + seatCount);

        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        cancellationRequest.setStatus(RequestStatus.APPROVED);
        cancellationRequestService.save(cancellationRequest);

        return "Booking cancelled successfully!";
    }


    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }
}
