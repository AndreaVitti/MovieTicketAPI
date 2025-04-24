package com.project.ticketapp.bookingTicketApp.controller;

import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUserbyId(@PathVariable("id") Long id) {
        Response response = userService.getUserbyId(id);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    @GetMapping("/getTicketsById/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> getTicketsByUserId(@PathVariable("id") Long id) {
        Response response = userService.getTicketsByUserId(id);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
        Response response = userService.deleteUser(id);
        return  ResponseEntity.status(response.getHttpCode()).body(response);
    }
}
