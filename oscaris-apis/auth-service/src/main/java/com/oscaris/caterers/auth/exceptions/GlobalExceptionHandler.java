package com.oscaris.caterers.auth.exceptions;

import com.oscaris.caterers.auth.exceptions.errors.EmailAddressNotFoundException;
import com.oscaris.caterers.auth.exceptions.errors.RolesNotRegisteredException;
import com.oscaris.caterers.auth.exceptions.errors.UserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Author: kev.Ameda
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EmailAddressNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(RolesNotRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleRolesNotRegistered(RolesNotRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserExistsException ex) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new ErrorResponse(302, ex.getMessage(), LocalDateTime.now()));
    }

}
