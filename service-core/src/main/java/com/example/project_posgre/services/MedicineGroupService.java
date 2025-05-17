package com.example.project_posgre.services;

import com.example.project_posgre.models.MedicineGroup;

import java.util.List;
import java.util.Optional;

public interface MedicineGroupService {
    MedicineGroup create(MedicineGroup medicineGroup);
    MedicineGroup update(Long id, MedicineGroup medicineGroup);
    void delete(Long id);
    Optional<MedicineGroup> getById(Long id);
    List<MedicineGroup> getAll();
}
