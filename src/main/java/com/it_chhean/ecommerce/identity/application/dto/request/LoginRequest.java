package com.it_chhean.ecommerce.identity.application.dto.request;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
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
        String password
) {}