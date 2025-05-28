package com.example.project_posgre.controllers;

import com.example.project_posgre.models.MedicalRecord;
import com.example.project_posgre.models.User;
import com.example.project_posgre.services.MedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<MedicalRecord> getAllRecords() {
        return medicalRecordService.getAllRecords();
    }
    @GetMapping("/patients/{id}")
    public List<MedicalRecord> getAllRecordsByPatientId(@PathVariable Long id) {
        return medicalRecordService.getMedicalRecordByPatient(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getRecordById(id));
    }

    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<MedicalRecord> createRecord(@RequestBody MedicalRecord record, @AuthenticationPrincipal User user) {
        System.out.println(user.getPhoneNumber());
        return ResponseEntity.ok(medicalRecordService.createRecord(record));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> updateRecord(@PathVariable Long id, @RequestBody MedicalRecord record) {
        return ResponseEntity.ok(medicalRecordService.updateRecord(id, record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        medicalRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}