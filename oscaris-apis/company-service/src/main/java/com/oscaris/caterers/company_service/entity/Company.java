package com.oscaris.caterers.company_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Author: kev.Ameda
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "companies")
public class Company {
    @Id
    private String companyId;
    private String companyName;
    private String kraPin;
    private String contactPerson;
    private int count;
    private Address address;
    private String phoneNumber;

}
