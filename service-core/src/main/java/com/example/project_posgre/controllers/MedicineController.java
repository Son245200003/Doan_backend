package com.example.project_posgre.controllers;

import com.example.project_posgre.dtos.requests.MedicineRequest;
import com.example.project_posgre.models.Medicine;
import com.example.project_posgre.repository.MedicineRepository;
import com.example.project_posgre.services.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;
    private final MedicineRepository medicineRepository;


    @GetMapping
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @PostMapping
    public ResponseEntity<Medicine> createMedicine(@RequestBody MedicineRequest medicine) {
        return ResponseEntity.ok(medicineService.createMedicine(medicine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestBody MedicineRequest medicine) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, medicine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }
    // MedicineController.java (hoặc MedicineImportController.java)
    @PostMapping("/{id}/import")
    public ResponseEntity<Medicine> importMedicine(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        Medicine medicine = medicineService.getMedicineById(id);
        if (medicine == null) {
            return ResponseEntity.notFound().build();
        }
        medicine.setStockQuantity(medicine.getStockQuantity() + quantity);
        medicineRepository.save(medicine);
        return ResponseEntity.ok(medicine);
    }

}