package com.example.project_posgre.services;

import com.example.project_posgre.dtos.requests.InvoiceRequestDTO;
import com.example.project_posgre.models.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice createInvoice(InvoiceRequestDTO invoice);
    Invoice updateInvoice(Long id, InvoiceRequestDTO invoice);
    void deleteInvoice(Long id);
    Invoice getInvoiceById(Long id);
    List<Invoice> getAllInvoices();
    List<Invoice> getInvoicesByPatient(Long idPatient);
}

