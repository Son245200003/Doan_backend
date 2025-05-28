package com.example.project_posgre.controllers;

import com.example.project_posgre.models.Admission;
import com.example.project_posgre.services.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    @GetMapping
    public ResponseEntity<List<Admission>> getAll() {
        return ResponseEntity.ok(admissionService.getAllAdmissions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admission> getById(@PathVariable Long id) {
        return admissionService.getAdmissionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/patient/{id}")
    public ResponseEntity<?> getByIdPatient(@PathVariable Long id) {
        return ResponseEntity.ok(admissionService.findByPatientId(id));
    }
    @PostMapping
    public ResponseEntity<Admission> create(@RequestBody Admission admission) {
        return ResponseEntity.ok(admissionService.createAdmission(admission));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admission> update(@PathVariable Long id, @RequestBody Admission admission) {
        return ResponseEntity.ok(admissionService.updateAdmission(id, admission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        admissionService.deleteAdmission(id);
        return ResponseEntity.noContent().build();
    }
}
