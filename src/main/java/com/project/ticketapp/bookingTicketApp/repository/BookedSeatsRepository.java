package com.project.ticketapp.bookingTicketApp.repository;

import com.project.ticketapp.bookingTicketApp.entity.BookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedSeatsRepository extends JpaRepository<BookedSeat, Long> {
}
