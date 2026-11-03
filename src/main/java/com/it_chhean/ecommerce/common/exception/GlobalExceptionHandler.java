package com.it_chhean.ecommerce.common.exception;

import com.it_chhean.ecommerce.common.payload.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        ErrorResponse response = ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
           );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        ErrorResponse response = ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception ex,
            HttpServletRequest request
    ) {
        ErrorResponse response = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
