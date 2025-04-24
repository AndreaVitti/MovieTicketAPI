package com.project.ticketapp.bookingTicketApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String summary;
    private String genre;
    @Transient
    private int numOfSeats;
    private LocalDateTime start;
    @Transient
    private LocalDateTime finish;
    private int duration;
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> tickets;
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<BookedSeat> bookedSeats;

    public LocalDateTime getFinish() {
        return this.start.plusMinutes(this.duration);
    }

    public int getNumOfSeats() {
        return Seat.getSeatTotal();
    }
}
