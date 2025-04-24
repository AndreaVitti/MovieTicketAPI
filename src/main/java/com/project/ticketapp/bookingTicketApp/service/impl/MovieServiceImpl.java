package com.project.ticketapp.bookingTicketApp.service.impl;

import com.project.ticketapp.bookingTicketApp.dto.MovieDTO;
import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.entity.Movie;
import com.project.ticketapp.bookingTicketApp.exception.CustomException;
import com.project.ticketapp.bookingTicketApp.mapper.Utility;
import com.project.ticketapp.bookingTicketApp.repository.MovieRepository;
import com.project.ticketapp.bookingTicketApp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Response addMovie(MovieDTO movieDTO) {
        Response response = new Response();
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setSummary(movieDTO.getSummary());
        movie.setGenre(movieDTO.getGenre());
        movie.setStart(movieDTO.getStart());
        movie.setDuration(movieDTO.getDuration());

        /*Check if the movie has a valid time frame*/
        try {
            if (!isMovieValid(movie)) {
                throw new CustomException("Movie is not valid");
            }
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        movieRepository.save(movie);
        return response;
    }

    @Override
    public Response getAllMovies() {
        Response response = new Response();
        List<Movie> movies = movieRepository.findAll();

        /*Check if there are any movies*/
        if (movies.isEmpty()) {
            response.setHttpCode(404);
            response.setMessage("No movies found");
            return response;
        }
        response.setHttpCode(200);
        response.setMovieDTOSList(Utility.mapMovieListtoMovieDTOList(movies));
        return response;
    }

    @Override
    public Response getMoviebyId(Long id) {
        Response response = new Response();
        MovieDTO movieDTO;

        /*Check there is a movie based on the provided id*/
        try {
            movieDTO = Utility.mapMovietoMovieDTO(movieRepository
                    .findById(id)
                    .orElseThrow(() -> new CustomException("The movie " + id + "found")));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        response.setMovieDTO(movieDTO);
        return response;
    }

    @Override
    public Response getMoviebyName(String name) {
        Response response = new Response();
        List<MovieDTO> movieDTOS;

        /*Check there are movies based on the provided title*/
        try {
            movieDTOS = Utility.mapMovieListtoMovieDTOList(movieRepository
                    .findByName(name)
                    .orElseThrow(() -> new CustomException("The movie " + name + " does not exist")));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        response.setMovieDTOSList(movieDTOS);
        return response;
    }

    @Override
    public Response getAvailableByName(String name) {
        Response response = new Response();
        List<Movie> movies;
        List<Movie> moviesAvailable;

        try {
            /*Check there are movies based on the provided name*/
            movies = movieRepository
                    .findByName(name)
                    .orElseThrow(() -> new CustomException("The movie " + name + " does not exist"));

            /*Check if the movies have enough seats and have not aired*/
            moviesAvailable = movies.stream().filter(movie -> isMovieAvailable(movie)).toList();
            if (moviesAvailable.isEmpty()) {
                throw new CustomException("The movie " + name + " is not available");
            }
        } catch (CustomException e) {
            response.setHttpCode(200);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        response.setMovieDTOSList(Utility.mapMovieListtoMovieDTOList(moviesAvailable));
        return response;
    }

    @Override
    public Response getMoviebyGenre(String genre) {
        Response response = new Response();
        List<MovieDTO> movieDTOS;

        /*Check there are movies based on the provided genre*/
        try {
            movieDTOS = Utility.mapMovieListtoMovieDTOList(movieRepository
                    .findByGenre(genre)
                    .orElseThrow(() -> new CustomException("There are no movies for the " + genre + " genre.")));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        response.setMovieDTOSList(movieDTOS);
        return response;
    }

    @Override
    public Response getTicketsById(Long id) {
        Response response = new Response();
        Movie movie;

        /*Check there is a movie based on the provided id*/
        try {
            movie = (movieRepository
                    .findById(id)
                    .orElseThrow(() -> new CustomException("The movie " + id + " does not exist")));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        response.setTicketDTOList(Utility.mapMovietoMovieDTOplusTicketDTOS(movie).getTickets());
        return response;
    }

    @Override
    public Response deleteMovie(Long id) {
        Response response = new Response();

        /*Check there is a movie based on the provided id*/
        try {
            movieRepository.findById(id).orElseThrow(() -> new CustomException("The movie " + id + " does not exist"));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        movieRepository.deleteById(id);
        return response;
    }

    @Override
    public Response updateMovie(Long id, String name, String summary, String genre, LocalDateTime start, Integer duration) {
        Response response = new Response();
        Movie movie;

        /*Check there is a movie based on the provided id*/
        try {
            movie = movieRepository
                    .findById(id)
                    .orElseThrow(() -> new CustomException("The movie " + name + " does not exist"));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }

        /*Check if the parameters provided are valid*/
        if (name != null && !(movie.getName().equals(name))) {
            movie.setName(name);
        }
        if (summary != null && !(movie.getSummary().equals(summary))) {
            movie.setSummary(summary);
        }
        if (genre != null && !(movie.getGenre().equals(genre))) {
            movie.setGenre(genre);
        }
        if (start.isAfter(LocalDateTime.now()) && !(movie.getStart().equals(start))) {
            movie.setStart(start);
        }
        if (duration >= 1 && (movie.getDuration() != duration)) {
            movie.setDuration(duration);
        }
        movieRepository.save(movie);
        response.setHttpCode(200);
        return response;
    }

    /*Checks if a movie can be added based on a comparison between its time frame and the others'*/
    private boolean isMovieValid(Movie movie) {
        return movieRepository.findAll().stream().noneMatch(
                m -> movie.getStart().isAfter(m.getStart()) && movie.getStart().isBefore(m.getFinish())
                        || movie.getFinish().isAfter(m.getStart()) && movie.getFinish().isBefore(m.getFinish())
                        || movie.getStart().isEqual(m.getStart()) || movie.getFinish().isEqual(m.getFinish())
        );
    }

    /*Check if the movie is available for booking*/
    @Override
    public boolean isMovieAvailable(Movie movie) {

        /*Check if the movie has not aired yet*/
        if (movie.getStart().isBefore(LocalDateTime.now())) {
            return false;
        }

        /*Check there are not booked tickets*/
        if (movie.getTickets() == null) {
            return true;
        }

        /*Check there the number of available seats*/
        return movie.getTickets().size() < movie.getNumOfSeats();
    }
}
