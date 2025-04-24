package com.project.ticketapp.bookingTicketApp.repository;

import com.project.ticketapp.bookingTicketApp.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatsRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findBySeatNumb(String seatNumb);
}
