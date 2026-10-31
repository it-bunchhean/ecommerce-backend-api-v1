package com.it_chhean.ecommerce.common.exception;

import com.it_chhean.ecommerce.common.payload.ErrorResponse;
import com.it_chhean.ecommerce.common.payload.FieldErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            WebRequest req
    ) {
        log.error("error occurred {}", ex);
        String path = req.getDescription(false).replace("uri=", "");
        ErrorResponse error = ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.toString(),
                ex.getMessage(),
                path
           );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
