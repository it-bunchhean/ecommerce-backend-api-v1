package com.it_chhean.ecommerce.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException{

    private final HttpStatus status;

    protected AppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
