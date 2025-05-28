package com.example.project_posgre.controllers;

import com.example.project_posgre.dtos.requests.PatientServiceRequest;
import com.example.project_posgre.models.Patient;
import com.example.project_posgre.models.PatientService;
import com.example.project_posgre.models.Service;
import com.example.project_posgre.repository.PatientRepository;
import com.example.project_posgre.repository.PatientServiceRepository;
import com.example.project_posgre.repository.ServiceRepository;
import com.example.project_posgre.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient-services")
public class PatientServiceController {
    private final ServiceService serviceService;
    private final ServiceRepository serviceRepository;
    private final PatientServiceRepository patientServiceRepository;
    private final PatientRepository patientRepository;

    @PostMapping("")
    public ResponseEntity<?> addPatientServices(@RequestBody PatientServiceRequest req) {
        Patient patient = patientRepository.findById(req.getPatientId()).orElseThrow();
        for (Long serviceId : req.getServiceIds()) {
            Service service = serviceRepository.findById(serviceId).orElseThrow();
            patientServiceRepository.save(new PatientService(null, patient, service, LocalDateTime.now(), PatientService.Status.UNPAID));
        }
        return ResponseEntity.ok().build();
    }
    @GetMapping("")
    public ResponseEntity<?> getPatientServices(@RequestParam("patientId") Long patientId) {

        return ResponseEntity.ok(patientServiceRepository.findByPatientId(patientId));
    }
}
