package com.aston.bankapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionDto> handle(BaseException e, HttpServletRequest request) {
        log.error("Exception occurred during request {}", request.getRequestURI(), e);
        return ResponseEntity
                .status(e.getStatus())
                .body(ExceptionDto.builder()
                        .status(e.getStatus())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionDto> handleEntityValidationException(ValidationException e, HttpServletRequest request) {
        log.error("Entity validation exception occurred during request {}", request.getRequestURI(), e);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ExceptionDto.builder()
                        .status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .message("invalid data")
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("Validation exception occurred during request {}", request.getRequestURI(), e);
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ExceptionDto.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("invalid request data")
                        .build());
    }
}
