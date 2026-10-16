package com.practice.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientLimitException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientLimit(
            InsufficientLimitException ex) {

        ErrorResponse response = new ErrorResponse(
                "INSUFFICIENT_LIMIT",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(LimitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLimitNotFound(
            LimitNotFoundException ex) {

        ErrorResponse response = new ErrorResponse(
                "LIMIT_NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}