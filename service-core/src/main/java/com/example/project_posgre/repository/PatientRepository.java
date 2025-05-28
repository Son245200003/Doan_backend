package com.example.project_posgre.repository;

import com.example.project_posgre.models.Medicine;
import com.example.project_posgre.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByPatientCode(String patientCode);
    boolean existsByIdentityCard(String identityCard);
    long countByPatientCodeStartingWith(String prefix);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.identityCard) LIKE LOWER(CONCAT('%', :identityCard, '%')) OR LOWER(p.phone) LIKE LOWER(CONCAT('%', :phoneNumber, '%'))")
    List<Patient> findByIdentityCardOrPhone(@Param("identityCard") String identityCard, @Param("phoneNumber") String phoneNumber);

    List<Patient> findAllByOrderByIdAsc();

}