package com.oscaris.caterers.auth.exceptions.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: kev.Ameda
 */
@ResponseStatus(value = HttpStatus.FOUND)
public class UserExistsException extends RuntimeException{
    public UserExistsException() {
    }

    public UserExistsException(String message) {
        super(message);
    }
}
