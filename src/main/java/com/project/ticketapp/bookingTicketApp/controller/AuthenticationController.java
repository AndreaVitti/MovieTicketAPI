package com.project.ticketapp.bookingTicketApp.controller;

import com.project.ticketapp.bookingTicketApp.dto.AuthRequest;
import com.project.ticketapp.bookingTicketApp.dto.RegisterRequest;
import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /*
    Post request to register user not already inside SecurityContextHolder
    It takes as parameter a request containing the user's personal information
     */

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody RegisterRequest regRequest) {
        Response response = authenticationService.register(regRequest);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    /*
    Post request to authenticate a user
    It takes as parameter a request containing the user's username and password
     */

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        Response response = authenticationService.authenticate(authRequest);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    /*
    Post request to receive a new access token through a refresh token
    It takes as parameter a request containing the refresh token
     */

    @PostMapping("/refreshToken")
    public ResponseEntity<Response> refreshToken(HttpServletRequest refreshRequest, HttpServletResponse refreshResponse) {
        Response response = authenticationService.refreshToken(refreshRequest, refreshResponse);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }
}
