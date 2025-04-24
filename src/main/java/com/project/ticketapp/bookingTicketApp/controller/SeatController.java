package com.project.ticketapp.bookingTicketApp.controller;

import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllSeats() {
        Response response = seatService.getAllSeats();
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    @GetMapping("/getAvailableSeatsByMovieId/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> getAvailableSeatsByMovieId(@PathVariable("id") Long id) {
        Response response = seatService.getAvailableSeatsByMovieId(id);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    @PostMapping("/setSeats/{num}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> setNumOfSeats(@PathVariable("num") Integer num) {
        Response response = seatService.setNumOfSeats(num);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }
}
