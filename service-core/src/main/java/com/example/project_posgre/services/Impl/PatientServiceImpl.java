package com.example.project_posgre.services.Impl;

import com.example.project_posgre.exception.NotFoundException;
import com.example.project_posgre.models.Patient;
import com.example.project_posgre.repository.PatientRepository;
import com.example.project_posgre.services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));
    }

    @Override
    public Patient createPatient(Patient patient) {
        if (patient.getIdentityCard() != null && patientRepository.existsByIdentityCard(patient.getIdentityCard())) {
            throw new NotFoundException("Identity card already exists.");
        }
        String newPatientCode = generateUniquePatientCode();
        patient.setPatientCode(newPatientCode);

        return patientRepository.save(patient);
    }
    private String generateUniquePatientCode() {
        int year = java.time.Year.now().getValue();

        long countForYear = patientRepository.countByPatientCodeStartingWith("BN-" + year + "-");
        long nextNumber = countForYear + 1;

        String code = String.format("BN-%d-%03d", year, nextNumber);

        while (patientRepository.existsByPatientCode(code)) {
            nextNumber++;
            code = String.format("BN-%d-%03d", year, nextNumber);
        }

        return code;
    }
    @Override
    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = getPatientById(id);

        patient.setFullName(patientDetails.getFullName());
        patient.setDateOfBirth(patientDetails.getDateOfBirth());
        patient.setGender(patientDetails.getGender());
        patient.setIdentityCard(patientDetails.getIdentityCard());
        patient.setInsuranceNumber(patientDetails.getInsuranceNumber());
        patient.setAddress(patientDetails.getAddress());
        patient.setPhone(patientDetails.getPhone());
        patient.setEmail(patientDetails.getEmail());
        patient.setBloodType(patientDetails.getBloodType());
        patient.setAllergies(patientDetails.getAllergies());
        patient.setNotes(patientDetails.getNotes());

        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
    @Override
    public List<Patient> findByIdentityCardOrPhone(String identityCard, String phoneNumber) {
        return patientRepository.findByIdentityCardOrPhone(identityCard, phoneNumber);
    }
}
