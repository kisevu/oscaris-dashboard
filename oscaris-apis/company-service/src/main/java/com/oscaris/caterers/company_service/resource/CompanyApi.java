package com.oscaris.caterers.company_service.resource;

import com.oscaris.caterers.company_service.entity.Company;
import com.oscaris.caterers.company_service.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Author: kev.Ameda
 */
@RestController
@RequestMapping("/companies")
public class CompanyApi {

    private final CompanyService companyService;

    public CompanyApi(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCompany(@RequestBody Company company ){
        this.companyService.addCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> getCompanies(){
        List<Company> companies = this.companyService.getCompanies();
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanies());
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<?> getCompany(@PathVariable String companyName ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.getCompany(companyName));

    }

    @DeleteMapping
    public ResponseEntity<?> removeCompany( @PathVariable String companyName ){
        companyService.removeCompany(companyName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Company Deleted");
    }
}
