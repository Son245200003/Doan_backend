package com.example.project_posgre.services;

import com.example.project_posgre.models.Patient;

import java.util.List;
import java.util.Optional;


public interface PatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(Long id);
    Patient createPatient(Patient patient);
    Patient updatePatient(Long id, Patient patient);
    void deletePatient(Long id);
    List<Patient> findByIdentityCardOrPhone(String identityCard, String phoneNumber);

}