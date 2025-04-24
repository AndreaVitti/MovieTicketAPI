package com.project.ticketapp.bookingTicketApp.dto;

import com.project.ticketapp.bookingTicketApp.type.Role;
import com.project.ticketapp.bookingTicketApp.validation.PhoneIsValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    private String firstname;
    @NotBlank(message = "Last name is required")
    private String lastname;
    @Email(message = "Email is required")
    private String email;
    @NotBlank(message = "Password required")
    @Size(min = 12, message = "Password too long")
    @Size(max = 20, message = "Password too long")
    private String password;
    @PhoneIsValid(message = "Invalid phone number")
    @Size(max = 18, message = "Invalid phone number")
    private String phone;
    private Role role;
}
