package com.it_chhean.ecommerce.common.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
    public static ErrorResponse of(
            HttpStatus status,
            String message,
            String path
    ) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.name(),
                message,
                path
        );
    }
}
