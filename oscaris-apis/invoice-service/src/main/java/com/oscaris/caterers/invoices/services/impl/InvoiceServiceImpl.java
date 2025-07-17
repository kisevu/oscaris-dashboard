package com.oscaris.caterers.invoices.services.impl;

import com.lowagie.text.DocumentException;
import com.oscaris.caterers.invoices.client.CompanyServiceClient;
import com.oscaris.caterers.invoices.config.SequentialNumberGen;
import com.oscaris.caterers.invoices.model.Company;
import com.oscaris.caterers.invoices.model.Invoice;
import com.oscaris.caterers.invoices.pdf.InvoicePDFGenerator;
import com.oscaris.caterers.invoices.repository.InvoiceRepository;
import com.oscaris.caterers.invoices.services.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: kev.Ameda
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoicePDFGenerator invoicePDFGenerator;

    private final InvoiceRepository invoiceRepository;
    private final CompanyServiceClient companyServiceClient;

    private final SequentialNumberGen sequentialNumberGen;

    public InvoiceServiceImpl(InvoicePDFGenerator invoicePDFGenerator,
                              InvoiceRepository invoiceRepository,
                              CompanyServiceClient companyServiceClient,
                              SequentialNumberGen sequentialNumberGen) {
        this.invoicePDFGenerator = invoicePDFGenerator;
        this.invoiceRepository = invoiceRepository;
        this.companyServiceClient = companyServiceClient;
        this.sequentialNumberGen = sequentialNumberGen;
    }

    @Override
    public void generateInvoicePdfBytesThymeLeaf(Invoice invoice) {
        byte[] bytes = invoicePDFGenerator.generateInvoicePdfBytesThymeLeaf(invoice);
    }

    @Override
    public byte[] retrieveInvoiceFile(String invoiceId) {
        return new byte[0];
    }

    @Override
    public Object retrieveInvoice(String invoiceId) {
        return null;
    }

    @Override
    public byte[] generateInvoicePdf(String invoiceId) {
        return new byte[0];
    }

    @Override
    public void generateInvoice(Invoice invoice) {
        Optional<Company> company = companyServiceClient.findByCompanyName(invoice.getCustomerName());
        if(Objects.isNull(company) ){
            log.info("Company could not be found");
            log.info("Invoice could not be created for");
            return;
        } else {
            log.info("Company found, proceeding to invoice generation...");
            Invoice invoice1 = Invoice.builder()
                    .invoiceNumber("oscaris-caterers-"+
                            sequentialNumberGen.getNextFormatted("INV")+"-"+
                            LocalDate.now().getMonth().toString()+
                            "-" + String.valueOf(LocalDate.now().getYear())+"-"+
                            company )
                    .time(ZonedDateTime.now(ZoneId.of("Africa/Nairobi")).toLocalDateTime())
                    .amountDue(invoice.getAmountDue())
                    .customerName(company.get().getCompanyName())
                    .build();
            Invoice savedInvoice = invoiceRepository.save(invoice1);
            try {
                // Generate and store the PDF in GridFS
                String fileId = invoicePDFGenerator.storeInvoicePdf(savedInvoice);

                // Update the invoice with the PDF file ID
                savedInvoice.setPdfFileId(fileId);
                invoiceRepository.save(savedInvoice); // persist the update

                log.info("Saved Invoice with PDF ID: {}", fileId);
            } catch (IOException | DocumentException e) {
                log.error("Failed to generate or store invoice PDF", e);
            }
            log.info("Saved Invoice:{}",savedInvoice);
        }
    }
}
