package com.aston.bankapp.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionDto(
        HttpStatus status,
        String message
) {
}
