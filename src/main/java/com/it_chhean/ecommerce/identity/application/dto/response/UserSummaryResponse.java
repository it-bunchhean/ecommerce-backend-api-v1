package com.it_chhean.ecommerce.identity.application.dto.response;

import jakarta.validation.constraints.NotBlank;

public record UserSummaryResponse(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String role
) {}
