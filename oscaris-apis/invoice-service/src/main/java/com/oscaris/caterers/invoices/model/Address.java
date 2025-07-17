package com.oscaris.caterers.invoices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: kev.Ameda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String street;
    private String city;
    private String constituency;
    private String building;
}
