package com.oscaris.caterers.auth.dtos.responses;

import lombok.*;

import java.time.LocalDate;

/**
 * Author: kev.Ameda
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StudentRecordsResponse {

    private Long studentId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String className;
    private int score;
    private int status;
    private String photoPath;
}
