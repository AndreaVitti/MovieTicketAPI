package com.project.ticketapp.bookingTicketApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Seats")
public class Seat {

    private static int seatTotal;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String seatNumb;
    @OneToMany(mappedBy = "seat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedSeat> bookedSeats;

    public static int getSeatTotal() {
        return Seat.seatTotal;
    }

    public static void setSeatTotal(int seatTotal) {
        Seat.seatTotal = seatTotal;
    }
}
