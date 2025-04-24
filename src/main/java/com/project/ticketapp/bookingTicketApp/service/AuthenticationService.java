package com.project.ticketapp.bookingTicketApp.service;

import com.project.ticketapp.bookingTicketApp.dto.AuthRequest;
import com.project.ticketapp.bookingTicketApp.dto.RegisterRequest;
import com.project.ticketapp.bookingTicketApp.dto.Response;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthenticationService {

    Response register(RegisterRequest request);

    Response authenticate(AuthRequest request);

    Response refreshToken(HttpServletRequest request, HttpServletResponse response);
}
