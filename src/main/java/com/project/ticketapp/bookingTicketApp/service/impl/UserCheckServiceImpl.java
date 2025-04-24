package com.project.ticketapp.bookingTicketApp.service.impl;

import com.project.ticketapp.bookingTicketApp.dto.Response;
import com.project.ticketapp.bookingTicketApp.entity.User;
import com.project.ticketapp.bookingTicketApp.service.UserCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCheckServiceImpl implements UserCheckService {
    public Response checkUser(Long userId, Response response) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedUser.getRole().name().equals("USER") && !loggedUser.getId().equals(userId)) {
            response.setHttpCode(403);
            response.setMessage("User not authorized");
            return response;
        }
        return null;
    }
}
