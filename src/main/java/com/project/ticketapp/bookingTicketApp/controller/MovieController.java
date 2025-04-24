package com.project.ticketapp.bookingTicketApp.controller;

import com.project.ticketapp.bookingTicketApp.dto.MovieDTO;
import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    /*
    Post request that let admins add movies to the DB
    It takes as parameter a DTO containing the movie information
     */

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addMovie(@Valid @RequestBody MovieDTO movieDTO) {
        Response response = movieService.addMovie(movieDTO);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    /*
    Get request that let users and admins view all movies
     */

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> getAllMovies() {
        Response response = movieService.getAllMovies();
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

     /*
    Get request that let admins view a movie based on its id
    It takes as parameter the id of the movie
     */

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getMoviebyId(@PathVariable("id") Long id) {
        Response response = movieService.getMoviebyId(id);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

     /*
    Get request that let users and admins view movies by their names
    It takes as parameter the title of the movie
     */

    @GetMapping("/getByName/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> getMoviebyName(@PathVariable("name") String name){
        Response response = movieService.getMoviebyName(name);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    /*
    Get request that let users and admins view all not fully booked movies by their names
    It takes as parameter the title of the movie
     */

    @GetMapping("/getAvailableByName/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> getAvailableByName(@PathVariable("name") String name){
        Response response = movieService.getAvailableByName(name);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    /*
    Get request that let users and admins view all movies based on their genre
    It takes as parameter the genre of the movie
     */

    @GetMapping("/getByGenre/{genre}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> getMoviebyGenre(@PathVariable("genre") String genre){
        Response response = movieService.getMoviebyGenre(genre);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

     /*
    Get request that let admins view all tickets of a specific movie
    It takes as a parameter the id of the movie
     */

    @GetMapping("/getTicketsById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getTicketById(@PathVariable("id") Long id) {
        Response response = movieService.getTicketsById(id);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

     /*
    Delete request that let admins delete a movie
    It takes as a parameter the id of the movie
     */

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteMovie(@PathVariable("id") Long id) {
        Response response = movieService.deleteMovie(id);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

     /*
    Put request that let admins update a movie info
    It takes as a parameter/s the value/s of the movie to be updated
     */

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateMovie(@PathVariable("id") Long id,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String summary,
                            @RequestParam(required = false) String genre,
                            @RequestParam(required = false) LocalDateTime start,
                            @RequestParam(required = false) Integer duration) {
        Response response = movieService.updateMovie(id, name, summary, genre, start, duration);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }
}
