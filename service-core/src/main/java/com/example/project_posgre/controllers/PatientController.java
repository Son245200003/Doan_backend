package com.example.project_posgre.controllers;

import com.example.project_posgre.models.Patient;
import com.example.project_posgre.services.Impl.SupabaseService;
import com.example.project_posgre.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final SupabaseService supabaseService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.createPatient(patient));
    }

    @PutMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public List<Patient> searchPatients(
            @RequestParam(required = false) String identityCard,
            @RequestParam(required = false) String phoneNumber) {

        if ((identityCard == null || identityCard.isBlank()) &&
                (phoneNumber == null || phoneNumber.isBlank())) {
            throw new IllegalArgumentException("At least one of identityCard or phoneNumber must be provided.");
        }

        return patientService.findByIdentityCardOrPhone(identityCard, phoneNumber);
    }


}
