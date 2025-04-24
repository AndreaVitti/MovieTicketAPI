package com.project.ticketapp.bookingTicketApp.authConfig;

import com.project.ticketapp.bookingTicketApp.entity.Token;
import com.project.ticketapp.bookingTicketApp.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLogOutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        Token jwtToken = tokenRepository.findByJwtToken(jwt).orElse(null);
        if (jwtToken != null) {
            jwtToken.setLogout(true);
            tokenRepository.save(jwtToken);
        }
    }
}
