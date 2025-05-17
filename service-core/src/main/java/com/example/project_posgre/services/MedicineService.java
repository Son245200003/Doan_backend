package com.example.project_posgre.services;

import com.example.project_posgre.dtos.requests.MedicineRequest;
import com.example.project_posgre.models.Medicine;

import java.util.List;

public interface MedicineService {
    List<Medicine> getAllMedicines();
    Medicine getMedicineById(Long id);
    Medicine createMedicine(MedicineRequest medicine);
    Medicine updateMedicine(Long id, MedicineRequest medicine);
    void deleteMedicine(Long id);
}