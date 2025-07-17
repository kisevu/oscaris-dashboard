package com.oscaris.caterers.invoices.client;

import com.oscaris.caterers.invoices.model.Company;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;
import java.util.Optional;

/**
 * Author: kev.Ameda
 */
public interface CompanyServiceClient {

    @GetExchange("")
    List<Company> findAll();

    @GetExchange("/{companyName}")
    Optional<Company> findByCompanyName(@PathVariable String companyName);
}
