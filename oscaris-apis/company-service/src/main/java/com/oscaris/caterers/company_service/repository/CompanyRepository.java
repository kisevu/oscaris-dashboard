package com.oscaris.caterers.company_service.repository;

import com.oscaris.caterers.company_service.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: kev.Ameda
 */
@Repository
public interface CompanyRepository extends MongoRepository<Company,String> {
    Optional<Company> findByCompanyName(String companyName);
}
