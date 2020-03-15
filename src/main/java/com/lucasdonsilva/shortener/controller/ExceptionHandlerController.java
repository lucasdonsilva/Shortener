package com.lucasdonsilva.shortener.controller;

import com.lucasdonsilva.shortener.exception.InvalidUrlException;
import com.lucasdonsilva.shortener.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> urlNotFoundException(Exception e) {

        log.error(e.getMessage());

        return status(NOT_FOUND).body(buildMessage(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> urlValidationException(MethodArgumentNotValidException e) {

        log.error(e.getMessage());

        return status(BAD_REQUEST).body(buildValidatorMessage(e.getBindingResult()));
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<Map<String, String>> invalidUrlException(Exception e) {

        log.error(e.getMessage());

        return status(UNPROCESSABLE_ENTITY).body(buildMessage(e.getMessage()));
    }

    private Map<String, String> buildValidatorMessage(BindingResult bindingResult) {

        List<FieldError> errors = bindingResult.getFieldErrors();
        Map<String, String> messages = new HashMap<>();
        errors.forEach(e -> messages.put(e.getField(), e.getDefaultMessage()));

        return messages;
    }

    private Map<String, String> buildMessage(String message) {
        return of("message", message);
    }
}