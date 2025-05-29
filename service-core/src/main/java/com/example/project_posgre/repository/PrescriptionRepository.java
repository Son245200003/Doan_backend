package com.example.project_posgre.repository;

import com.example.project_posgre.models.Prescription;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientIdAndStatus(Long patientId, Prescription.Status status);
    @Modifying
    @Transactional
    @Query("UPDATE Prescription p SET p.status = com.example.project_posgre.models.Prescription.Status.PAID WHERE p.patient.id = :patientId AND p.status = com.example.project_posgre.models.Prescription.Status.UNPAID")
    int markAllPrescriptionsPaidByPatient(Long patientId);
}