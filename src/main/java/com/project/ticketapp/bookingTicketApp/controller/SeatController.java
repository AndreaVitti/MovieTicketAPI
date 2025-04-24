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

    /*
    Get request that lets admins get all the seats
     */

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllSeats() {
        Response response = seatService.getAllSeats();
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    /*
    Get request that lets both admins and users get all available seats for a specific movie identified by its title
    It takes as parameter a request containing the movie title
     */

    @GetMapping("/getAvailableSeatsByMovieId/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> getAvailableSeatsByMovieId(@PathVariable("id") Long id) {
        Response response = seatService.getAvailableSeatsByMovieId(id);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    /*
    Post request that lets admins set the specific number of seats in the theater
    It takes as parameter a request containing the number of seats
     */

    @PostMapping("/setSeats/{num}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> setNumOfSeats(@PathVariable("num") Integer num) {
        Response response = seatService.setNumOfSeats(num);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }
}
