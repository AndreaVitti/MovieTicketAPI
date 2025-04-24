package com.project.ticketapp.bookingTicketApp.mapper;

import com.project.ticketapp.bookingTicketApp.dto.*;
import com.project.ticketapp.bookingTicketApp.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class Utility {

    public static UserDTO mapUsertoUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static TicketDTO mapTickettoTicketDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setType(ticket.getType());
        return ticketDTO;
    }

    public static MovieDTO mapMovietoMovieDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setName(movie.getName());
        movieDTO.setSummary(movie.getSummary());
        movieDTO.setGenre(movie.getGenre());
        movieDTO.setNumOfSeats(movie.getNumOfSeats());
        movieDTO.setStart(movie.getStart());
        movieDTO.setFinish(movie.getFinish());
        movieDTO.setDuration(movie.getDuration());
        return movieDTO;
    }

    public static SeatDTO mapSeattoSeatDTO(Seat seat) {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setId(seat.getId());
        seatDTO.setSeatTotal(Seat.getSeatTotal());
        seatDTO.setSeatNumb(seat.getSeatNumb());
        return seatDTO;
    }

    public static BookedSeatDTO mapBookedSeattoBookedSeatDTO(BookedSeat bookedSeat) {
        BookedSeatDTO bookedSeatDTO = new BookedSeatDTO();
        bookedSeatDTO.setId(bookedSeat.getId());
        bookedSeatDTO.setBookedSeatNumb(bookedSeat.getBookedSeatNumb());
        return bookedSeatDTO;
    }

    public static UserDTO mapUsertoUserDTOplusTicketDTOS(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        if (!user.getTickets().isEmpty()) {
            userDTO.setTickets(user.getTickets().stream()
                    .map(ticket -> Utility.mapTickettoTicketDTOplusUserDTOorMovieDTO(ticket, false))
                    .collect(Collectors.toList()));
        }
        return userDTO;
    }

    public static TicketDTO mapTickettoTicketDTOplusUserDTOorMovieDTO(Ticket ticket, boolean flag) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setPrice(ticket.getPrice());
        ticketDTO.setType(ticket.getType());
        if (flag) {
            ticketDTO.setUser(Utility.mapUsertoUserDTO(ticket.getUser()));
        } else {
            ticketDTO.setMovie(Utility.mapMovietoMovieDTO(ticket.getMovie()));
        }
        return ticketDTO;
    }

    public static TicketDTO mapTickettoTicketDTOplusUserDTOandMovieDTOandBookedSeat(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setPrice(ticket.getPrice());
        ticketDTO.setType(ticket.getType());
        ticketDTO.setUser(Utility.mapUsertoUserDTO(ticket.getUser()));
        ticketDTO.setMovie(Utility.mapMovietoMovieDTO(ticket.getMovie()));
        ticketDTO.setBookedSeat(Utility.mapBookedSeattoBookedSeatDTO(ticket.getBookedSeat()));
        return ticketDTO;
    }

    public static MovieDTO mapMovietoMovieDTOplusTicketDTOS(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setName(movie.getName());
        movieDTO.setSummary(movie.getSummary());
        movieDTO.setGenre(movie.getGenre());
        movieDTO.setNumOfSeats(movie.getNumOfSeats());
        movieDTO.setStart(movie.getStart());
        movieDTO.setFinish(movie.getFinish());
        movieDTO.setDuration(movie.getDuration());
        if (!movie.getTickets().isEmpty()) {
            movieDTO.setTickets(movie.getTickets().stream()
                    .map(ticket -> Utility.mapTickettoTicketDTOplusUserDTOorMovieDTO(ticket, true))
                    .collect(Collectors.toList()));
        }
        return movieDTO;
    }

    public static List<UserDTO> mapUserListtoUserDTOList(List<User> users) {
        return users.stream()
                .map(user -> Utility.mapUsertoUserDTO(user))
                .collect(Collectors.toList());
    }

    public static List<TicketDTO> mapTicketListtoTicketDTOList(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> Utility.mapTickettoTicketDTO(ticket))
                .collect(Collectors.toList());
    }

    public static List<MovieDTO> mapMovieListtoMovieDTOList(List<Movie> movies) {
        return movies.stream()
                .map(movie -> Utility.mapMovietoMovieDTO(movie))
                .collect(Collectors.toList());
    }

    public static List<SeatDTO> mapSeatListtoSeatDTOList(List<Seat> seats) {
        return seats.stream()
                .map(seat -> Utility.mapSeattoSeatDTO(seat))
                .collect(Collectors.toList());
    }

    public static List<BookedSeatDTO> mapBookedSeatListtoBookedSeatDTOList(List<BookedSeat> bookedSeats) {
        return bookedSeats.stream()
                .map(bookedSeat -> Utility.mapBookedSeattoBookedSeatDTO(bookedSeat))
                .collect(Collectors.toList());
    }
}
