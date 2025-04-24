package com.project.ticketapp.bookingTicketApp.dto;

import com.project.ticketapp.bookingTicketApp.entity.Movie;
import com.project.ticketapp.bookingTicketApp.entity.Seat;
import com.project.ticketapp.bookingTicketApp.entity.Ticket;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookedSeatDTO {
    private Long id;

    @NotBlank(message = "Booked seat number required")
    private String bookedSeatNumb;

    private Seat seat;

    private Ticket ticket;

    private Movie movie;
}
