package com.example.project_posgre.dtos.requests;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceDetailRequestDTO {
    private Long serviceId;   // optional
    private Long medicineId;  // optional
    private Long bedId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal amount;
    private String notes;
}