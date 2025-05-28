package com.example.project_posgre.repository;

import com.example.project_posgre.models.Invoice;
import com.example.project_posgre.models.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findAllByOrderByIdAsc();
    List<MedicalRecord> findByPatientId(long id);
}