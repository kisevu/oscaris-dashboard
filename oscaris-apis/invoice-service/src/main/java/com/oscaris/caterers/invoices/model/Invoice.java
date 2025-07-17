package com.oscaris.caterers.invoices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: kev.Ameda
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "invoices")
public class Invoice  {
    @Id
    private String invoiceId;
    private String invoiceNumber;
    private LocalDateTime time;
    private BigDecimal amountDue;
    private String customerName;
    private String companyId;
    private String pdfFileId;

}

