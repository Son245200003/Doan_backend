package com.example.project_posgre.repository;

import com.example.project_posgre.models.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientServiceRepository extends JpaRepository<PatientService, Long> {
    List<PatientService> findByPatientId(Long patientId);
    List<PatientService> findAllByOrderByIdAsc();
    List<PatientService> findByPatientIdAndStatus(Long patientId, PatientService.Status status);
    @Modifying
    @Transactional
    @Query("UPDATE PatientService ps SET ps.status = com.example.project_posgre.models.PatientService.Status.PAID WHERE ps.patient.id = :patientId AND ps.status = com.example.project_posgre.models.PatientService.Status.UNPAID")
    int markAllPatientServicesPaidByPatient(Long patientId);
}
