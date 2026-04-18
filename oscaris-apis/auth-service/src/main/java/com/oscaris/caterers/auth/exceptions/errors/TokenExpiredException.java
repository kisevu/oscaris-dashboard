package com.oscaris.caterers.auth.exceptions.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: kev.Ameda
 */
@ResponseStatus( value = HttpStatus.BAD_REQUEST)
public class TokenExpiredException extends RuntimeException{

    public TokenExpiredException() {
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
