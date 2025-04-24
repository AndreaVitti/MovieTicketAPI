package com.project.ticketapp.bookingTicketApp.service;

import com.project.ticketapp.bookingTicketApp.dto.Response;

public interface SeatService {
    Response setNumOfSeats(Integer num);

    Response getAllSeats();

    Response getAvailableSeatsByMovieId(Long id);
}
