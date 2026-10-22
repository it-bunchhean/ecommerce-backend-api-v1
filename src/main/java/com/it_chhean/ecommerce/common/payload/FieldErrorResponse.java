package com.it_chhean.ecommerce.common.payload;

public record FieldErrorResponse(
        String field,
        String reason
) {}
