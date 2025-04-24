package com.project.ticketapp.bookingTicketApp.dto;

import com.project.ticketapp.bookingTicketApp.type.Type;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class TicketDTO {
    private Long id;

    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private double price;

    private Type type;

    private MovieDTO movie;

    private UserDTO user;

    private BookedSeatDTO bookedSeat;
}

