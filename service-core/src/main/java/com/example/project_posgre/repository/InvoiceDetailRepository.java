package com.example.project_posgre.repository;

import com.example.project_posgre.models.Appointment;
import com.example.project_posgre.models.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
    List<InvoiceDetail> findAllByOrderByIdAsc();

}
