package com.redcare.rest.handler;

import com.redcare.exception.InvalidCreatedAfterException;
import com.redcare.rest.respose.BedRequestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCreatedAfterException.class)
    public ResponseEntity<?> handleValidationErrors(InvalidCreatedAfterException ex) {
        return ResponseEntity.badRequest().body(new BedRequestResponse(ex.getMessage()));
    }
}
