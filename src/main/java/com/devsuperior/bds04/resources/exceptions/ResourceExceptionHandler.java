package com.devsuperior.bds04.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        HttpStatus httpBadRequest = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(httpBadRequest.value());
        validationError.setError("Validation exception");
        validationError.setMessage(exception.getMessage());
        validationError.setPath(request.getRequestURI());

        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                validationError.addError(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(httpBadRequest).body(validationError);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<StandardError> entityNotFound(NoSuchElementException exception, HttpServletRequest request) {
        HttpStatus httpNotFound = HttpStatus.NOT_FOUND;

        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(httpNotFound.value());
        standardError.setError("Resource not found");
        standardError.setMessage(exception.getMessage());
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(httpNotFound).body(standardError);
    }
}
