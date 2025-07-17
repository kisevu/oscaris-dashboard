package com.oscaris.caterers.company_service.service;



import com.oscaris.caterers.company_service.entity.Company;

import java.util.List;

/**
 * Author: kev.Ameda
 */
public interface CompanyService {
    void addCompany(Company company);
    List<Company> getCompanies();
    void removeCompany(String company );

    Company getCompany(String companyName);
}
