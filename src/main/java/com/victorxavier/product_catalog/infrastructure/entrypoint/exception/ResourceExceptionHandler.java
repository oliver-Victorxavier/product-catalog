package com.victorxavier.product_catalog.infrastructure.entrypoint.exception;

import com.victorxavier.product_catalog.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError erro = new StandardError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(status.value());
        erro.setError("Resource not found");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError erro = new StandardError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(status.value());
        erro.setError("Database exception");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError erro = new ValidationError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(status.value());
        erro.setError("Validation exception");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            erro.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<StandardError> email(EmailException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError erro = new StandardError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(status.value());
        erro.setError("Email exception");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> runtime(RuntimeException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError erro = new StandardError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(status.value());
        erro.setError("Authentication failed");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());
        System.err.println("RuntimeException caught: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(status).body(erro);
    }
}