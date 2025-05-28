package com.example.project_posgre.repository;

import com.example.project_posgre.models.Admission;
import com.example.project_posgre.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    @Query("SELECT a FROM Admission a WHERE a.patient.id = :patientId")
    List<Admission> findByPatientId(@Param("patientId") Long patientId);
}

