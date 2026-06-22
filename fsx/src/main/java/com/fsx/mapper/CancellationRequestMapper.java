package com.fsx.mapper;

import com.fsx.dto.CancellationReqResponseDto;
import com.fsx.model.CancellationRequest;

public class CancellationRequestMapper {
    public static CancellationReqResponseDto mapToDto(CancellationRequest cancellationRequest){
        return new CancellationReqResponseDto(cancellationRequest.getId(),
                cancellationRequest.getPassenger().getId(),
                cancellationRequest.getPassenger().getName(),
                cancellationRequest.getReason(),
                cancellationRequest.getRequestedAt(),
                cancellationRequest.getBooking().getSchedule().getRoute().getSourceCity(),
                cancellationRequest.getBooking().getSchedule().getRoute().getDestinationCity(),
                cancellationRequest.getBooking().getSchedule().getDepartureDate(),
                cancellationRequest.getBooking().getSeatCount(),
                cancellationRequest.getBooking().getTotalAmount(),
                cancellationRequest.getStatus());
    }
}
