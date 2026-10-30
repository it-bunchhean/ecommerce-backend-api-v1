package com.it_chhean.ecommerce.identity.application.dto.request;

import jakarta.validation.constraints.*;

import java.util.Locale;

public record RegisterRequest(
        @NotBlank(message = "Email connot be blank")
        @Email(message = "Invalid email format")
        @Size(max = 50, message = "Email is too long")
        String email,

        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 30, message = "Username must be 3-30 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Username contains invalid characters")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(max = 8, message = "Password must be at learst 8 character long")
        String passwordHash,

        @Size(max = 50, message = "First name is too long")
        String firstName,

        @Size(max = 50, message = "Last name is too long")
        String lastName,

        @Pattern(regexp = "^\\+[1-9]\\d{7,14}$", message = "Phone must be in international format, e.g. +85512345678")
        String phoneNumber
) {
    public RegisterRequest {
        email = email != null ? email.trim().toLowerCase(Locale.ROOT) : null;
        username = trimToNull(username);
        firstName = trimToNull(firstName);
        lastName = trimToNull(lastName);
        phoneNumber = trimToNull(phoneNumber);
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @Override
    public String toString() {
        return "RegisterRequest[email=" + email + ", username=" + username + "]";
    }
}
