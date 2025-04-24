package com.project.ticketapp.bookingTicketApp.repository;

import com.project.ticketapp.bookingTicketApp.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
