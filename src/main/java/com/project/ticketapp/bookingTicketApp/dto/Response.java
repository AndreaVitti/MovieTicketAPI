package com.project.ticketapp.bookingTicketApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int httpCode;
    private String message;

    private String token;
    private String refreshToken;

    private UserDTO userDTO;
    private MovieDTO movieDTO;
    private TicketDTO ticketDTO;
    private SeatDTO seatDTO;
    private BookedSeatDTO bookedSeatDTO;
    private List<UserDTO> userDTOList;
    private List<MovieDTO> movieDTOSList;
    private List<TicketDTO> ticketDTOList;
    private List<SeatDTO> seatDTOList;
    private List<BookedSeatDTO> bookedSeatDTOList;
}
