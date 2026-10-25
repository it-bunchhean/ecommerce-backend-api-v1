package com.it_chhean.ecommerce.identity.application.dto;

public record RegisterRequest(
        String username,
        String email,
        String passwordHash
) {}
