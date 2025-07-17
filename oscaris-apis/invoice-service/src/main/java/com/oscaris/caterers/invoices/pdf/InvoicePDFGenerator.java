package com.oscaris.caterers.invoices.pdf;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.oscaris.caterers.invoices.model.Invoice;
import com.oscaris.caterers.invoices.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Base64;


/**
 * Author: kev.Ameda
 */

@Service
@Slf4j
public class InvoicePDFGenerator {
    private final InvoiceRepository invoiceRepository;

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations operations;
    private final TemplateEngine templateEngine;

    public InvoicePDFGenerator(GridFsTemplate gridFsTemplate,
                               GridFsOperations operations,
                               TemplateEngine templateEngine,
                               InvoiceRepository invoiceRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.operations = operations;
        this.templateEngine = templateEngine;
        this.invoiceRepository = invoiceRepository;
    }


    public byte[] retrieveInvoicePdf(String fileId) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));

        if (file == null) throw new FileNotFoundException("File not found: " + fileId);

        GridFsResource resource = operations.getResource(file);

        try (InputStream is = resource.getInputStream()) {
            return StreamUtils.copyToByteArray(is);
        }
    }

    public byte[] generateInvoicePdfBytesThymeLeaf(Invoice invoice) {
        try {
            //doesn't resolve in container but does in local dev
//            Path logoPath = Paths.get("src/main/resources/static/images/oscaris.png");

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/images/oscaris.png");
            if (inputStream == null) {
                throw new RuntimeException("Logo image not found in classpath: static/images/oscaris.png");
            }
//            byte[] logoBytes = Files.readAllBytes(logoPath);
            byte [] logoBytes = inputStream.readAllBytes();
            String logoBase64 = Base64.getEncoder().encodeToString(logoBytes);
            // 1. Prepare HTML using Thymeleaf
            Context context = new Context();
            context.setVariable("invoiceNumber", invoice.getInvoiceNumber());
            context.setVariable("customerName", invoice.getCustomerName());
            context.setVariable("amountDue", "ksh." + invoice.getAmountDue().toPlainString());
            context.setVariable("date", invoice.getTime().format(DateTimeFormatter.ofPattern("MMM dd',' yyyy")));
            context.setVariable("logo",logoBase64);

            String htmlContent = templateEngine.process("invoice", context);

            // 2. Render HTML to PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();

        } catch (IOException | DocumentException e) {
            throw new RuntimeException("Error generating invoice PDF", e);
        }
    }
    public String storeInvoicePdf(Invoice invoice) throws IOException, DocumentException {
        byte[] pdfBytes = this.generateInvoicePdfBytesThymeLeaf(invoice);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfBytes);
        return gridFsTemplate.store(inputStream, invoice.getInvoiceNumber() + ".pdf",
                "application/pdf").toString();
    }
    private PdfPCell getCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }


}
