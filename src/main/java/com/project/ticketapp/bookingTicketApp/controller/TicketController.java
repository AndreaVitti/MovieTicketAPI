package com.project.ticketapp.bookingTicketApp.controller;

import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.dto.TicketDTO;
import com.project.ticketapp.bookingTicketApp.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    /*
    Post request that let admins and users add tickets to a user for a movie
    It takes as parameters the user id, the movie id and a DTO containing the ticket information
     */

    @PostMapping("/add/{UserId}/{MovieId}/{SeatNum}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> addTicket(@PathVariable("UserId") Long userId, @PathVariable("MovieId") Long movieId, @PathVariable("SeatNum") String seatNum, @Valid @RequestBody TicketDTO ticketDTO) {
        Response response = ticketService.addTicket(userId, movieId, seatNum, ticketDTO);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    /*
    Get request that let admins get all the tickets
     */

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAll() {
        Response response = ticketService.getAll();
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }


    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getTicketById(@PathVariable("id") Long id) {
        Response response = ticketService.getTicketById(id);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }


    /*
    Post request that let admins and users delete tickets
    It takes as parameters the ticket id
     */

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> deleteTicket(@PathVariable("id") Long id) {
        Response response = ticketService.deleteTicket(id);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }
}
