package com.oscaris.caterers.auth.exceptions.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: kev.Ameda
 */
@ResponseStatus( value = HttpStatus.BAD_REQUEST)
public class RolesNotRegisteredException extends RuntimeException{
    public RolesNotRegisteredException() {
    }

    public RolesNotRegisteredException(String message) {
        super(message);
    }
}
