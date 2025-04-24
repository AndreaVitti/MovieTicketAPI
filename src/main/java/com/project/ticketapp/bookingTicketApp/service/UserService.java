package com.project.ticketapp.bookingTicketApp.service;

import com.project.ticketapp.bookingTicketApp.dto.Response;

public interface UserService {
    Response getAllUsers();

    Response getUserbyId(Long id);

    Response getTicketsByUserId(Long id);

    Response deleteUser(Long id);
}
