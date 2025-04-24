package com.project.ticketapp.bookingTicketApp.service.impl;

import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.entity.User;
import com.project.ticketapp.bookingTicketApp.exception.CustomException;
import com.project.ticketapp.bookingTicketApp.mapper.Utility;
import com.project.ticketapp.bookingTicketApp.repository.UserRepository;
import com.project.ticketapp.bookingTicketApp.service.UserCheckService;
import com.project.ticketapp.bookingTicketApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCheckService userCheckService;

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        List<User> users = userRepository.findAll();

        /*Check is there are any users*/
        if (users.isEmpty()) {
            response.setHttpCode(404);
            response.setMessage("No users found");
            return response;
        }
        response.setUserDTOList(Utility.mapUserListtoUserDTOList(users));
        response.setHttpCode(200);
        return response;
    }

    @Override
    public Response getUserbyId(Long id) {
        Response response = new Response();
        User user;

        /*Check there is a user based on the provided id*/
        try {
            user = userRepository.findById(id).orElseThrow(() -> new CustomException("User " + id + " not found"));
            response.setHttpCode(200);
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        response.setUserDTO(Utility.mapUsertoUserDTO(user));
        return response;
    }

    @Override
    public Response getTicketsByUserId(Long id) {
        Response response = new Response();
        User user;

        /*Check there is a user based on the provided id*/
        try {
            user = userRepository.findById(id).orElseThrow(() -> new CustomException("User " + id + " not found"));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }

        /*Check if a user is accessing his own resources*/
        if (userCheckService.checkUser(id, response) != null) {
            return response;
        }
        response.setHttpCode(200);
        response.setTicketDTOList(Utility.mapUsertoUserDTOplusTicketDTOS(user).getTickets());
        return response;
    }

    @Override
    public Response deleteUser(Long id) {
        Response response = new Response();

        /*Check there is a user based on the provided id*/
        try {
            userRepository.findById(id).orElseThrow(() -> new CustomException("User " + id + " not found"));
        } catch (CustomException e) {
            response.setHttpCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setHttpCode(200);
        userRepository.deleteById(id);
        return response;
    }
}
