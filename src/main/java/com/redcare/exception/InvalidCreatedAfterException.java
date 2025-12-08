package com.redcare.exception;

public class InvalidCreatedAfterException extends RuntimeException{
    public InvalidCreatedAfterException(String message) {
        super(message);
    }
}
