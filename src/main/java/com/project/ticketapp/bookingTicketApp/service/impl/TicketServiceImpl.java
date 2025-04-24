package com.project.ticketapp.bookingTicketApp.service.impl;

import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.dto.TicketDTO;
import com.project.ticketapp.bookingTicketApp.entity.*;
import com.project.ticketapp.bookingTicketApp.exception.CustomException;
import com.project.ticketapp.bookingTicketApp.mapper.Utility;
import com.project.ticketapp.bookingTicketApp.repository.*;
import com.project.ticketapp.bookingTicketApp.service.MovieService;
import com.project.ticketapp.bookingTicketApp.service.TicketService;
import com.project.ticketapp.bookingTicketApp.service.UserCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    @Value(value = "${discount.children}")
    private double childDisc;
    @Value(value = "${discount.elderly}")
    private double elderDisc;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final MovieRepository movieRepository;
    private final MovieService movieService;
    private final SeatsRepository seatsRepository;
    private final BookedSeatsRepository bookedSeatsRepository;
    private final UserCheckService userCheckService;

    @Override
    @Transactional
    public Response addTicket(Long userId, Long movieId, String seatNum, TicketDTO ticketDTO) {
        Response response = new Response();
        Ticket ticket = new Ticket();
        BookedSeat bookedSeat = new BookedSeat();
        Movie movie;
        User user;
        Seat seat;
        try {
            movie = movieRepository.findById(movieId).orElseThrow(() -> new CustomException("Movie " + movieId + " not found"));
            if (!movieService.isMovieAvailable(movie)) {
                throw new CustomException("No available movie found");
            }
            user = userRepository.findById(userId).orElseThrow(() -> new CustomException("User " + userId + " not found"));
            seat = seatsRepository.findBySeatNumb(seatNum).orElseThrow(() -> new CustomException("Seat " + seatNum + " not found"));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        if (userCheckService.checkUser(userId, response) != null) {
            return response;
        }
        List<String> occupiedSeats = movie.getBookedSeats().stream().map(booked -> booked.getSeat().getSeatNumb()).toList();
        if (occupiedSeats.contains(seat.getSeatNumb())) {
            response.setHttpCode(403);
            response.setMessage("Seat already booked");
            return response;
        }
        bookedSeat.setSeat(seat);
        bookedSeat.setBookedSeatNumb(seat.getSeatNumb());
        bookedSeat.setTicket(ticket);
        bookedSeat.setMovie(movie);
        bookedSeatsRepository.save(bookedSeat);
        //ticket.setPrice(ticketDTO.getPrice());
        if (ticketDTO.getType().name().equals("CHILD")) {
            ticket.setPrice(ticketDTO.getPrice()*childDisc);
        } else if (ticketDTO.getType().name().equals("OVER65")) {
            ticket.setPrice(ticketDTO.getPrice()*elderDisc);
        } else {
            ticket.setPrice(ticketDTO.getPrice());
        }

        ticket.setType(ticketDTO.getType());
        ticket.setUser(user);
        ticket.setMovie(movie);
        ticket.setBookedSeat(bookedSeat);
        response.setHttpCode(200);
        ticketRepository.save(ticket);
        return response;
    }

    @Override
    public Response getAll() {
        Response response = new Response();
        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty()) {
            response.setHttpCode(404);
            response.setMessage("No tickets found");
            return response;
        }
        response.setHttpCode(200);
        List<TicketDTO> ticketDTOS = tickets.stream().map(ticket -> Utility.mapTickettoTicketDTOplusUserDTOandMovieDTOandBookedSeat(ticket)).toList();
        response.setTicketDTOList(ticketDTOS);
        return response;
    }

    @Override
    public Response getTicketById(Long id) {
        Response response = new Response();
        Ticket ticket;
        try {
            ticket = ticketRepository.findById(id).orElseThrow(() -> new CustomException("Ticket " + id + " not found"));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        response.setTicketDTO(Utility.mapTickettoTicketDTOplusUserDTOandMovieDTOandBookedSeat(ticket));
        return response;
    }

    @Override
    public Response deleteTicket(Long id) {
        Response response = new Response();
        Ticket ticket;
        try {
            ticket = ticketRepository.findById(id).orElseThrow(() -> new CustomException("Ticket " + id + " not found"));
            if (!ticket.getMovie().getStart().isAfter(LocalDateTime.now())) {
                throw new CustomException("Ticket " + id + " not valid");
            }
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        if (userCheckService.checkUser(ticket.getUser().getId(), response) != null) {
            return response;
        }
        response.setHttpCode(200);
        ticketRepository.deleteById(id);
        return response;
    }
}
