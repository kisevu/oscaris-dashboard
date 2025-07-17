package com.oscaris.caterers.company_service.repository;

import com.oscaris.caterers.company_service.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: kev.Ameda
 */
@Repository
public interface AddressRepository extends MongoRepository<Address,String> {
}
