package com.project.ticketapp.bookingTicketApp.dto;

import com.project.ticketapp.bookingTicketApp.type.Role;
import com.project.ticketapp.bookingTicketApp.validation.PhoneIsValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    @NotBlank(message = "First name required")
    private String firstname;
    @NotBlank(message = "Last name required")
    private String lastname;
    @Email(message = "Email required")
    private String email;
    @PhoneIsValid(message = "Phone number required")
    private String phone;
    private Role role;
    private List<TicketDTO> tickets;
}
