package com.oscaris.caterers.invoices.resource;

import com.oscaris.caterers.invoices.model.Invoice;
import com.oscaris.caterers.invoices.services.InvoiceService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Author: kev.Ameda
 */
@RestController
@RequestMapping("/invoices")
public class InvoiceApi {
    private final InvoiceService invoiceService;

    public InvoiceApi(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> generateInvoice(@RequestBody Invoice invoice ){
        invoiceService.generateInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> retrieveInvoice(@RequestParam String invoiceId ){
        return ResponseEntity.ok().body(invoiceService.retrieveInvoice(invoiceId));
    }

    @GetMapping("/file/{invoiceId}")
    public ResponseEntity<?> retrieveInvoiceFile(@PathVariable String invoiceId ){
        byte[] pdfBytes = invoiceService.retrieveInvoiceFile(invoiceId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("invoice_" + invoiceId + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/{invoiceId}/download-pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable String invoiceId) {
        byte[] pdfBytes = invoiceService.generateInvoicePdf(invoiceId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("invoice_" + invoiceId + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


}
