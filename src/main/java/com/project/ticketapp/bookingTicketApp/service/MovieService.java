package com.project.ticketapp.bookingTicketApp.service;

import com.project.ticketapp.bookingTicketApp.dto.MovieDTO;
import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.entity.Movie;

import java.time.LocalDateTime;


public interface MovieService {
    Response addMovie(MovieDTO movieDTO);

    Response getAllMovies();

    Response getMoviebyId(Long id);

    Response getMoviebyName(String name);

    Response getAvailableByName(String name);

    Response getMoviebyGenre(String genre);

    Response getTicketsById(Long id);

    Response deleteMovie(Long id);

    Response updateMovie(Long id, String name, String summary, String genre, LocalDateTime start, Integer duration);

    boolean isMovieAvailable(Movie movie);
}
