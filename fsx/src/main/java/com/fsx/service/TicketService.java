package com.fsx.service;

import com.fsx.dto.PassengerSeatDto;
import com.fsx.dto.TicketResponseDto;
import com.fsx.enums.BookingStatus;
import com.fsx.exception.InvalidSeatException;
import com.fsx.mapper.TicketMapper;
import com.fsx.model.Booking;
import com.fsx.model.Ticket;
import com.fsx.repository.TicketRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public void addTicket(Booking booking, List<Integer> seatNumbers) {

        if (seatNumbers == null || seatNumbers.isEmpty()) {
            throw new InvalidSeatException("Seat must be chosen...");
        }
        List<Ticket> tickets = seatNumbers.stream().map(seatNumber -> {
                    Ticket ticket = new Ticket();
                    ticket.setBooking(booking);
                    ticket.setSeatNumber(seatNumber);
                    return ticket;
                }).toList();
        ticketRepository.saveAll(tickets);
    }

    public List<Ticket> getByBookingId(int id) {
        return ticketRepository.findByBookingId(id);
    }

    public List<Ticket> getTicketsByBookingId(int bookingId) {
       return ticketRepository.findByBookingId(bookingId);
    }

    public List<Integer> getBookedSeats(int scheduleId) {
        return ticketRepository.getBookedSeats(scheduleId);
    }

    public int countReservedSeats(
            int scheduleId,
            List<PassengerSeatDto> seats,
            List<BookingStatus> activeStatuses
    ) {

        List<Integer> seatNumbers = seats.stream()
                .map(PassengerSeatDto::seatNumber)
                .toList();

        return ticketRepository.countReservedSeats(
                scheduleId,
                seatNumbers,
                activeStatuses
        );
    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<TicketResponseDto> getTickets(int bookingId) {
        List<Ticket> tickets = getTicketsByBookingId(bookingId);
        return tickets.stream().map(ticketMapper::mapEntityToDto).toList();
    }
}
