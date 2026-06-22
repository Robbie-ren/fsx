package com.fsx.controller;

import com.fsx.dto.CancellationReqResponseDto;
import com.fsx.dto.CancellationRequestDto;
import com.fsx.enums.RequestStatus;
import com.fsx.model.CancellationRequest;
import com.fsx.service.CancellationRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cancel")
@CrossOrigin(origins = "http://localhost:5173")
public class CancellationRequestController {
    private final CancellationRequestService cancellationRequestService;

    @PostMapping("/cancel-request/{bookingId}")
    public CancellationRequest requestCancellation(
            @PathVariable int bookingId,
            @RequestBody CancellationRequestDto dto
    ) {
        return cancellationRequestService.requestCancellation(bookingId, dto.reason());

    }

    @GetMapping("/cancel-requests")
    public List<CancellationReqResponseDto> getAllPendingRequests(Principal principal){
        String username = principal.getName();
        return cancellationRequestService.getAllPendingRequests(username);

    }

    @PostMapping("/reject-request/{requestId}")
    public CancellationRequest rejectRequest(@PathVariable int requestId){
        return cancellationRequestService.rejectRequest(requestId);

    }
}
