package com.oscaris.caterers.auth.exceptions.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: kev.Ameda
 */
@ResponseStatus( value = HttpStatus.BAD_REQUEST)
public class EmailSendFailedException extends RuntimeException{

    public EmailSendFailedException() {
    }

    public EmailSendFailedException(String message) {
        super(message);
    }

    public EmailSendFailedException(String message, EmailSendFailedException ex) {
        super(message,ex);
    }
}
