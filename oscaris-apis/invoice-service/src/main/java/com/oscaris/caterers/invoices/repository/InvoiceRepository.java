package com.oscaris.caterers.invoices.repository;

import com.oscaris.caterers.invoices.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: kev.Ameda
 */
@Repository
public interface InvoiceRepository extends MongoRepository<Invoice,String> {

}
