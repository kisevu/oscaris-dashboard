package com.oscaris.caterers.company_service.service.impl;

import com.oscaris.caterers.common.exceptions.GenericException;
import com.oscaris.caterers.company_service.entity.Address;
import com.oscaris.caterers.company_service.entity.Company;
import com.oscaris.caterers.company_service.repository.AddressRepository;
import com.oscaris.caterers.company_service.repository.CompanyRepository;
import com.oscaris.caterers.company_service.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Author: kev.Ameda
 */
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public void addCompany(Company company) {
        Address address = Address.builder()
                .city(company.getAddress().getCity())
                .street(company.getAddress().getStreet())
                .building(company.getAddress().getBuilding())
                .build();
        Address save = addressRepository.save(address);
        Company company1 = Company.builder()
                .address(save)
                .companyName(company.getCompanyName())
                .count(company.getCount())
                .kraPin(company.getKraPin())
                .contactPerson(company.getContactPerson())
                .phoneNumber(company.getPhoneNumber())
                .build();

        companyRepository.save(company1);
    }

    @Override
    public List<Company> getCompanies() {
       return  companyRepository.findAll().stream()
                .toList();
    }

    @Override
    public void removeCompany(String companyName) {
        Company company = this.companyByName(companyName);
        boolean removed = false;
        if(removed){
            companyRepository.delete(company);
        } else {
            throw new GenericException("company "+companyName+"already removed.");
        }
    }

    private Company companyByName(String companyName ){
        Company company = companyRepository.findByCompanyName(companyName)
                .orElseThrow(
                        () -> new GenericException("company " + companyName + "could not be found."));
        if (Objects.isNull(company)){
            return null;
        } else {
            return company;
        }
    }

    @Override
    public Company getCompany(String companyName) {
        return this.companyByName(companyName);
    }
}
