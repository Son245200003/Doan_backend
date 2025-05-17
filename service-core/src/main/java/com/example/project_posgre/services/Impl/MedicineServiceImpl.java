package com.example.project_posgre.services.Impl;

import com.example.project_posgre.dtos.requests.MedicineRequest;
import com.example.project_posgre.models.Medicine;
import com.example.project_posgre.models.MedicineGroup;
import com.example.project_posgre.repository.MedicineGroupRepository;
import com.example.project_posgre.repository.MedicineRepository;
import com.example.project_posgre.services.MedicineService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineGroupRepository medicineGroupRepository;

    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @Override
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicine not found with id: " + id));
    }

    @Override
    public Medicine createMedicine(MedicineRequest request) {
        MedicineGroup medicineGroup=medicineGroupRepository.findById(request.getMedicineGroup()).orElseThrow();
        Medicine medicine = new Medicine();
        medicine.setMedicineName(medicine.getMedicineName());
        medicine.setMedicineGroup(medicineGroup);
        medicine.setActiveIngredient(medicine.getActiveIngredient());
        medicine.setUnit(medicine.getUnit());
        medicine.setPrice(medicine.getPrice());
        medicine.setStockQuantity(medicine.getStockQuantity());
        medicine.setExpiryDate(medicine.getExpiryDate());
        medicine.setSupplier(medicine.getSupplier());
        medicine.setDescription(medicine.getDescription());
        medicine.setNotes(medicine.getNotes());
        medicine.setStatus(medicine.getStatus());
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine updateMedicine(Long id, MedicineRequest updatedMedicine) {
        MedicineGroup medicineGroup=medicineGroupRepository.findById(updatedMedicine.getMedicineGroup()).orElseThrow();

        Medicine medicine = getMedicineById(id);

        medicine.setMedicineName(updatedMedicine.getMedicineName());
        medicine.setMedicineGroup(medicineGroup);
        medicine.setActiveIngredient(updatedMedicine.getActiveIngredient());
        medicine.setUnit(updatedMedicine.getUnit());
        medicine.setPrice(updatedMedicine.getPrice());
        medicine.setStockQuantity(updatedMedicine.getStockQuantity());
        medicine.setExpiryDate(updatedMedicine.getExpiryDate());
        medicine.setSupplier(updatedMedicine.getSupplier());
        medicine.setDescription(updatedMedicine.getDescription());
        medicine.setNotes(updatedMedicine.getNotes());
        medicine.setStatus(updatedMedicine.getStatus());

        return medicineRepository.save(medicine);
    }

    @Override
    public void deleteMedicine(Long id) {
        if (!medicineRepository.existsById(id)) {
            throw new EntityNotFoundException("Medicine not found with id: " + id);
        }
        medicineRepository.deleteById(id);
    }
}
