package com.oscaris.caterers.auth.exceptions;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Author: kev.Ameda
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timeStamp;

}
