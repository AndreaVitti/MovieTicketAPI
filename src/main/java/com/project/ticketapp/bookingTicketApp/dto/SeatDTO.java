package com.project.ticketapp.bookingTicketApp.dto;

import com.project.ticketapp.bookingTicketApp.entity.BookedSeat;
import lombok.Data;

import java.util.List;

@Data
public class SeatDTO {
    private Long id;

    private int seatTotal;

    private String seatNumb;

    private List<BookedSeat> bookedSeats;
}
