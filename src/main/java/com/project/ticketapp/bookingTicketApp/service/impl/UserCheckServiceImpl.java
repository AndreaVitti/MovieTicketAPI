package com.project.ticketapp.bookingTicketApp.service.impl;

import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.entity.User;
import com.project.ticketapp.bookingTicketApp.service.UserCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/*Check if a user is accessing his own resources*/
@Service
@RequiredArgsConstructor
public class UserCheckServiceImpl implements UserCheckService {
    public Response checkUser(Long userId, Response response) {

        /*Get currently logged user*/
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        /*Check if the currently logged user id is equal to the user id of the requested resource*/
        if (loggedUser.getRole().name().equals("USER") && !loggedUser.getId().equals(userId)) {
            response.setHttpCode(403);
            response.setMessage("User not authorized");
            return response;
        }
        return null;
    }
}
