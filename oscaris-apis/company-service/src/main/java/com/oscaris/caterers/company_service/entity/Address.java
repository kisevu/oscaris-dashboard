package com.oscaris.caterers.company_service.entity;

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
    private String building;
}
