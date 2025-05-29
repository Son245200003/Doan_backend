package com.example.project_posgre.dtos.requests;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceRequestDTO {
    private Long patientId;
    private Date invoiceDate;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal finalAmount;
    private BigDecimal paid;
    private String paymentMethod; // CASH, CARD, TRANSFER
    private String status; // PENDING, PAID, CANCELLED
    private String notes;
    private List<InvoiceDetailRequestDTO> invoiceDetails;

}
