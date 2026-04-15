package com.oscaris.caterers.auth.exceptions.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: kev.Ameda
 */

@ResponseStatus( value = HttpStatus.NOT_FOUND, reason = "Email provided is not available")
public class EmailAddressNotFoundException extends RuntimeException {
    public EmailAddressNotFoundException() {
    }

    public EmailAddressNotFoundException(String message) {
        super(message);
    }


}
