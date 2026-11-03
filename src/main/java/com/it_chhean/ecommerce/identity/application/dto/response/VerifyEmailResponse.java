package com.it_chhean.ecommerce.identity.application.dto.response;

import jakarta.validation.constraints.NotBlank;

public record VerifyEmailResponse(
        @NotBlank String success,
        @NotBlank String message,
        @NotBlank String acessToken,
        @NotBlank UserSummaryResponse user
) {}
