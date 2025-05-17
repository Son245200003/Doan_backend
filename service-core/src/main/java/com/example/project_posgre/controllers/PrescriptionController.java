package com.example.project_posgre.controllers;

import com.example.project_posgre.models.Prescription;
import com.example.project_posgre.services.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService service;

    @GetMapping
    public List<Prescription> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Prescription getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Prescription create(@RequestBody Prescription prescription) {
        return service.save(prescription);
    }

    @PutMapping("/{id}")
    public Prescription update(@PathVariable Long id, @RequestBody Prescription prescription) {
        return service.update(id, prescription);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}