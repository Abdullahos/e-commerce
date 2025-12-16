package com.udacity.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password validation failed.")
public class PasswordException extends RuntimeException {

    public PasswordException() {
        super();
    }

    public PasswordException(String message) {
        super(message);
    }
}
