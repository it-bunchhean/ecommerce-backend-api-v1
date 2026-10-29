package com.it_chhean.ecommerce.identity.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Email connot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at learst 8 character long")
        String passwordHash,

        String firstName,

        String lastName,

        String phoneNumber
) {
    public RegisterRequest {
        email = email != null ? email.trim() : null;
        firstName = firstName != null ? firstName.trim() : null;
        lastName = lastName != null ? lastName.trim() : null;
    }
}
