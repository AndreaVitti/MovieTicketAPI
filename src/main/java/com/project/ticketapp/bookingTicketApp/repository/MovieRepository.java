package com.project.ticketapp.bookingTicketApp.repository;

import com.project.ticketapp.bookingTicketApp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<List<Movie>> findByName(String name);

    Optional<List<Movie>> findByGenre(String genre);
}
