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
        return medicineRepository.findAllByOrderByIdAsc();
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
        medicine.setMedicineName(request.getMedicineName());
        medicine.setMedicineGroup(medicineGroup);
        medicine.setUnit(request.getUnit());
        medicine.setPrice(request.getPrice());
        medicine.setStockQuantity(request.getStockQuantity());
        medicine.setExpiryDate(request.getExpiryDate());
        medicine.setDescription(request.getDescription());
        medicine.setStatus(request.getStatus());
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine updateMedicine(Long id, MedicineRequest updatedMedicine) {
        MedicineGroup medicineGroup=medicineGroupRepository.findById(updatedMedicine.getMedicineGroup()).orElseThrow();

        Medicine medicine = getMedicineById(id);

        medicine.setMedicineName(updatedMedicine.getMedicineName());
        medicine.setMedicineGroup(medicineGroup);
        medicine.setUnit(updatedMedicine.getUnit());
        medicine.setPrice(updatedMedicine.getPrice());
        medicine.setStockQuantity(updatedMedicine.getStockQuantity());
        medicine.setExpiryDate(updatedMedicine.getExpiryDate());
        medicine.setDescription(updatedMedicine.getDescription());
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
