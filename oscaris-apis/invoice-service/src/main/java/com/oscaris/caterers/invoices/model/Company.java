package com.oscaris.caterers.invoices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: kev.Ameda
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Company {
    private String companyName;
    private String kraPin;
    private String contactPerson;
    private int count;
    private Address address;
    private String phoneNumber;
}
