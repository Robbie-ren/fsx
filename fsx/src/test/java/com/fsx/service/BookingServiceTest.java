package com.fsx.service;

import com.fsx.dto.PaginatedBookingRespDto;
import com.fsx.enums.BookingStatus;
import com.fsx.exception.ResourceNotFoundException;
import com.fsx.model.*;
import com.fsx.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking1;
    private Booking booking2;
    private Booking booking3;

    private Schedule schedule;
    private Route route;
    @BeforeEach
    public void sampleData() {
        booking1 = new Booking();
        Passenger passenger1 = new Passenger();
        passenger1.setName("Robbie");

        booking1.setPassenger(passenger1);
        booking1.setId(1);
        booking1.setBookingStatus(BookingStatus.CONFIRMED);
        booking1.setSeatCount(2);
        booking1.setTotalAmount(500);

        booking2 = new Booking();
        Passenger passenger2 = new Passenger();
        passenger2.setName("John");

        booking2.setPassenger(passenger2);
        booking2.setId(2);
        booking2.setBookingStatus(BookingStatus.CONFIRMED);
        booking2.setSeatCount(3);
        booking2.setTotalAmount(700);

        route = new Route();
        route.setSourceCity("Bangalore");
        route.setDestinationCity("Chennai");

        Bus bus = new Bus();
        bus.setBusName("Express");
        bus.setBusNumber("TN12323");

        schedule = new Schedule();
        schedule.setRoute(route);
        schedule.setBus(bus);
        schedule.setDepartureDate(LocalDate.now());
        schedule.setDepartureTime(LocalTime.now());

        booking1.setSchedule(schedule);
        booking2.setSchedule(schedule);
    }

    @Test
    void getAll_ReturnSomething() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<Booking> bookings = new PageImpl<>(
                List.of(booking1, booking2),
                pageable,
                2
        );

        when(bookingRepository.search(any(String.class), any(Pageable.class)))
                .thenReturn(bookings);

        PaginatedBookingRespDto result = bookingService.getAllBookings(1, 2, "ch");

        assertThat(result.totalRecords()).isEqualTo(2);
        assertThat(result.totalPages()).isEqualTo(1);
        assertThat(result.bookings()).hasSize(2);


    }

    @Test
    void getAll_ReturnEmpty() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<Booking> bookings = Page.empty(pageable);

        when(bookingRepository.search(any(String.class), any(Pageable.class)))
                .thenReturn(bookings);

        PaginatedBookingRespDto result = bookingService.getAllBookings(1, 2, "ch");

        assertThat(result.totalRecords()).isEqualTo(0);
        assertThat(result.totalPages()).isEqualTo(0);
        assertThat(result.bookings()).isEmpty();
    }

    @Test
    void getById_bookingExists(){
        when(bookingRepository.findById(100)).thenReturn(Optional.of(booking1));

        assertThat(bookingService.findById(100).getId()).isEqualTo(1);
        assertThat(bookingService.findById(100).getTotalAmount()).isEqualTo(500);
        assertThat(bookingService.findById(100)).isEqualTo(booking1);
    }

    @Test
    void getById_bookingDoesNotExist(){
        when(bookingRepository.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> bookingService.findById(100)).
                isInstanceOf(ResourceNotFoundException.class).
                hasMessage("Invalid booking id");

        verify(bookingRepository, times(1)).findById(100);
    }


    @Test
    void delete_mustDeleteAndReturnNothing(){
        when(bookingRepository.findById(100)).thenReturn(Optional.of(booking2));


        doNothing().when(bookingRepository).deleteById(100);

        bookingService.delete(100);


        verify(bookingRepository, times(1)).deleteById(100);
        verify(bookingRepository, times(1)).findById(100);
    }


}