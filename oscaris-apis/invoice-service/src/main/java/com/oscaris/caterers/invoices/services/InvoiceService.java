package com.oscaris.caterers.invoices.services;

import com.oscaris.caterers.invoices.model.Invoice;

/**
 * Author: kev.Ameda
 */


public interface InvoiceService {
    void generateInvoicePdfBytesThymeLeaf(Invoice invoice);
    byte[] retrieveInvoiceFile(String invoiceId);

    Object retrieveInvoice(String invoiceId);

    byte[] generateInvoicePdf(String invoiceId);
    void generateInvoice(Invoice invoice);
}
