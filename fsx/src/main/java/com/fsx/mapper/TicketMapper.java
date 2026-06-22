package com.fsx.mapper;

import com.fsx.dto.TicketRespDto;
import com.fsx.dto.TicketResponseDto;
import com.fsx.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public TicketRespDto mapToDto(Ticket ticket){
        return new TicketRespDto(ticket.getId(),
                ticket.getBooking().getPassenger().getName(),
                ticket.getSeatNumber());
    }

    public TicketResponseDto mapEntityToDto(Ticket ticket){
        return new TicketResponseDto(ticket.getId(),
                ticket.getBooking().getId(),
                ticket.getSeatNumber(),
                ticket.getPassengerName(),
                ticket.getPassengerAge(),
                ticket.getBooking().getSchedule().getDepartureDate(),
                ticket.getBooking().getSchedule().getDepartureTime(),
                ticket.getBooking().getSchedule().getArrivalTime(),
                ticket.getBooking().getSchedule().getRoute().getSourceCity(),
                ticket.getBooking().getSchedule().getRoute().getDestinationCity(),
                ticket.getBooking().getSchedule().getBus().getBusName(),
                ticket.getBooking().getSchedule().getBus().getBusNumber(),
                ticket.getBooking().getSchedule().getPrice());
    }
}
