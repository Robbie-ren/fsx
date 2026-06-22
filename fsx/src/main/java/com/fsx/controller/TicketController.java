package com.fsx.controller;

import com.fsx.dto.TicketResponseDto;
import com.fsx.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/get/{bookingId}")
    public List<TicketResponseDto> getTickets(@PathVariable int bookingId){
        return ticketService.getTickets(bookingId);
    }
}
