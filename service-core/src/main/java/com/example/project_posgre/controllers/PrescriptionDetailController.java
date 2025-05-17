package com.example.project_posgre.controllers;

import com.example.project_posgre.models.PrescriptionDetail;
import com.example.project_posgre.services.PrescriptionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescription-details")
public class PrescriptionDetailController {

    @Autowired
    private PrescriptionDetailService service;

    @GetMapping
    public List<PrescriptionDetail> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PrescriptionDetail getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public PrescriptionDetail create(@RequestBody PrescriptionDetail detail) {
        return service.save(detail);
    }

    @PutMapping("/{id}")
    public PrescriptionDetail update(@PathVariable Long id, @RequestBody PrescriptionDetail detail) {
        return service.update(id, detail);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}