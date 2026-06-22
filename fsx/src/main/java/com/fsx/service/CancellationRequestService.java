package com.fsx.service;

import com.fsx.dto.CancellationReqResponseDto;
import com.fsx.enums.BookingStatus;
import com.fsx.enums.RequestStatus;
import com.fsx.exception.ResourceNotFoundException;
import com.fsx.mapper.CancellationRequestMapper;
import com.fsx.model.Booking;
import com.fsx.model.CancellationRequest;
import com.fsx.repository.BookingRepository;
import com.fsx.repository.CancellationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CancellationRequestService {
    private final BookingRepository bookingRepository;
    private final CancellationRequestRepository cancellationRequestRepository;

    public CancellationRequest requestCancellation(int bookingId, String reason) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("Booking not found"));

        if (booking.getBookingStatus() == BookingStatus.CANCELLATION_REQUESTED) {
            throw new RuntimeException("Already requested");
        }

        CancellationRequest request = new CancellationRequest();
        request.setBooking(booking);
        request.setPassenger(booking.getPassenger());
        request.setReason(reason);
        request.setStatus(RequestStatus.PENDING);

        booking.setBookingStatus(BookingStatus.CANCELLATION_REQUESTED);
        bookingRepository.save(booking);

        return cancellationRequestRepository.save(request);
    }

    public List<CancellationReqResponseDto> getAllPendingRequests(String username) {
        List<CancellationRequest> list = cancellationRequestRepository.getAllPendingRequests(username);
        return list.stream().map(CancellationRequestMapper::mapToDto).toList();
    }

    public CancellationRequest getByBookingId(int bookingId) {
        return cancellationRequestRepository.findByBookingId(bookingId);
    }

    public void save(CancellationRequest cancellationRequest) {
        cancellationRequestRepository.save(cancellationRequest);
    }

    public CancellationRequest rejectRequest(int requestId) {
        CancellationRequest request = getById(requestId);
        request.setStatus(RequestStatus.REJECTED);
        Booking booking=request.getBooking();
        booking.setBookingStatus(BookingStatus.REQUEST_REJECTED);
        bookingRepository.save(booking);
        return cancellationRequestRepository.save(request);
    }

    public CancellationRequest getById(int requestId) {
        return cancellationRequestRepository.findById(requestId).orElseThrow(()->new ResourceNotFoundException("Request Not found"));
    }
}
