package com.project.ticketapp.bookingTicketApp.service.impl;

import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.entity.Movie;
import com.project.ticketapp.bookingTicketApp.entity.Seat;
import com.project.ticketapp.bookingTicketApp.exception.CustomException;
import com.project.ticketapp.bookingTicketApp.mapper.Utility;
import com.project.ticketapp.bookingTicketApp.repository.MovieRepository;
import com.project.ticketapp.bookingTicketApp.repository.SeatsRepository;
import com.project.ticketapp.bookingTicketApp.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatsRepository seatsRepository;
    private final MovieRepository movieRepository;

    @Override
    public Response setNumOfSeats(Integer num) {
        Response response = new Response();
        if (num < 1) {
            response.setHttpCode(400);
            response.setMessage("Can't be lower than 1");
            return response;
        }
        Seat.setSeatTotal(num);
        int count = 1;
        for (int i = 0; i < num; i++) {
            Seat seat = new Seat();
            seat.setSeatNumb(Integer.toString(count));
            count++;
            seatsRepository.save(seat);
        }
        response.setHttpCode(200);
        return response;
    }

    @Override
    public Response getAllSeats() {
        Response response = new Response();
        List<Seat> seats = seatsRepository.findAll();
        if (seats.isEmpty()) {
            response.setHttpCode(404);
            response.setMessage("No seats found");
            return response;
        }
        response.setHttpCode(200);
        response.setSeatDTOList(Utility.mapSeatListtoSeatDTOList(seats));
        return response;
    }

    @Override
    public Response getAvailableSeatsByMovieId(Long id) {
        Response response = new Response();
        Movie movie;
        try {
            movie = movieRepository
                    .findById(id)
                    .orElseThrow(() -> new CustomException("Movie " + id + " not found"));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        List<String> bookedSeats = movie.getBookedSeats().stream().map(bookedSeat -> bookedSeat.getBookedSeatNumb()).toList();
        response.setHttpCode(200);
        response.setSeatDTOList(Utility.mapSeatListtoSeatDTOList(seatsRepository
                .findAll()
                .stream()
                .filter(seat -> (!bookedSeats.contains(seat.getSeatNumb())))
                .toList()));
        return response;
    }
}
