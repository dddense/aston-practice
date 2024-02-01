package com.aston.bankapp.exception;

import org.springframework.http.HttpStatus;

public class InvalidPinException extends BaseException {
    public InvalidPinException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
