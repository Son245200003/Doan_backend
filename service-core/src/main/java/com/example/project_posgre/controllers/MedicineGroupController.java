package com.example.project_posgre.controllers;

import com.example.project_posgre.models.MedicineGroup;
import com.example.project_posgre.services.MedicineGroupService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicine-groups")
@CrossOrigin(origins = "*")
public class MedicineGroupController {

    private final MedicineGroupService medicineGroupService;

    public MedicineGroupController(MedicineGroupService medicineGroupService) {
        this.medicineGroupService = medicineGroupService;
    }

    @PostMapping
    public ResponseEntity<MedicineGroup> create(@RequestBody MedicineGroup medicineGroup) {
        return ResponseEntity.ok(medicineGroupService.create(medicineGroup));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineGroup> update(@PathVariable Long id, @RequestBody MedicineGroup medicineGroup) {
        return ResponseEntity.ok(medicineGroupService.update(id, medicineGroup));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicineGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineGroup> getById(@PathVariable Long id) {
        return medicineGroupService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Medicine group not found"));
    }

    @GetMapping
    public ResponseEntity<List<MedicineGroup>> getAll() {
        return ResponseEntity.ok(medicineGroupService.getAll());
    }
}
