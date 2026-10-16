package com.practice.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex) {

        ErrorResponse response =
                new ErrorResponse(
                        ex.getErrorCode(),
                        ex.getMessage(),
                        LocalDateTime.now());

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}