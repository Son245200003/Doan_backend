package com.example.project_posgre.repository;

import com.example.project_posgre.models.Invoice;
import com.example.project_posgre.models.InvoiceDetail;
import com.example.project_posgre.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByOrderByIdAsc();
    List<Invoice> findAllByPatientIdOrderByIdAsc(Long id);

}