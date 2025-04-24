package com.project.ticketapp.bookingTicketApp.dto;

import com.project.ticketapp.bookingTicketApp.entity.BookedSeat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDTO {
    private Long id;

    @NotBlank(message = "Movie title required")
    private String name;

    @NotBlank(message = "Summary required")
    private String summary;

    @NotBlank(message = "Genre required")
    private String genre;

    private int numOfSeats;

    @FutureOrPresent(message = "Invalid date")
    private LocalDateTime start;

    private LocalDateTime finish;

    @Min(value = 1, message = "Movie must be at least 1 minute long")
    private int duration;

    private List<TicketDTO> tickets;

    private List<BookedSeat> bookedSeats;
}
