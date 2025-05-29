package com.example.project_posgre.repository;

import com.example.project_posgre.models.Admission;
import com.example.project_posgre.models.Appointment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    @Query("SELECT a FROM Admission a WHERE a.patient.id = :patientId")
    List<Admission> findByPatientId(@Param("patientId") Long patientId);
    List<Admission> findByPatientIdAndStatusPay(Long patientId, Admission.Status statusPay);
    @Modifying
    @Transactional
    @Query("UPDATE Admission a SET a.statusPay = com.example.project_posgre.models.Admission.Status.PAID WHERE a.patient.id = :patientId AND a.statusPay = com.example.project_posgre.models.Admission.Status.UNPAID")
    int markAllAdmissionsPaidByPatient(Long patientId);
}

