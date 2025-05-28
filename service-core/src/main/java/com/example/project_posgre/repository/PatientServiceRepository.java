package com.example.project_posgre.repository;

import com.example.project_posgre.models.PatientService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientServiceRepository extends JpaRepository<PatientService, Long> {
    List<PatientService> findByPatientId(Long patientId);
}
