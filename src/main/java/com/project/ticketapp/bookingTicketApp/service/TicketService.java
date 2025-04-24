package com.project.ticketapp.bookingTicketApp.service;

import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.dto.TicketDTO;

public interface TicketService {
    Response addTicket(Long userId, Long movieId, String seatNum, TicketDTO ticketDTO);

    Response getAll();

    Response getTicketById(Long id);

    Response deleteTicket(Long id);
}
