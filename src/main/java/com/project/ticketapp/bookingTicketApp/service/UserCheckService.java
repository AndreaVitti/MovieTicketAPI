package com.project.ticketapp.bookingTicketApp.service;

import com.project.ticketapp.bookingTicketApp.dto.Response;

public interface UserCheckService {
    Response checkUser(Long userId, Response response);
}
