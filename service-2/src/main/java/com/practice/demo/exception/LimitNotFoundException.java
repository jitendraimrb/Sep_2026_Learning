package com.practice.demo.exception;
public class LimitNotFoundException extends RuntimeException {

    public LimitNotFoundException(String message) {
        super(message);
    }
}