package com.example.project_posgre.repository;

import com.example.project_posgre.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByPatientCode(String patientCode);
    boolean existsByIdentityCard(String identityCard);
    long countByPatientCodeStartingWith(String prefix);
    List<Patient> findByIdentityCardOrPhone(String identityCard, String phoneNumber);
}