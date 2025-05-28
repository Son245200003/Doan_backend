package com.example.project_posgre.services;

import com.example.project_posgre.models.Admission;

import java.util.List;
import java.util.Optional;

public interface AdmissionService {
    List<Admission> getAllAdmissions();
    Optional<Admission> getAdmissionById(Long id);
    Admission createAdmission(Admission admission);
    Admission updateAdmission(Long id, Admission admission);
    void deleteAdmission(Long id);
    List<Admission> findByPatientId(Long patientId);
}