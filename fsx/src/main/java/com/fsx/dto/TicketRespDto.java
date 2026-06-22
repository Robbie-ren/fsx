package com.fsx.dto;

public record TicketRespDto(
  int ticketId,
  String passengerName,
  int seatNumber
) {
}
