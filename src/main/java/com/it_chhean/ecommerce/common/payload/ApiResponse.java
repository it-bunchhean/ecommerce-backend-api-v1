package com.it_chhean.ecommerce.common.payload;

import java.time.Instant;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        Instant timestamp
) {
    public ApiResponse(boolean success, String message) {
        this(success, message, null, Instant.now());
    }
}
