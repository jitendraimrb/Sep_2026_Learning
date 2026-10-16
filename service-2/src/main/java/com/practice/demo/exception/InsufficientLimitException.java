package com.practice.demo.exception;

public class InsufficientLimitException extends RuntimeException {

    public InsufficientLimitException(String message) {
        super(message);
    }
}