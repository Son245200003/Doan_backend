package com.example.project_posgre.services.Impl;

import com.example.project_posgre.models.MedicineGroup;
import com.example.project_posgre.repository.MedicineGroupRepository;
import com.example.project_posgre.services.MedicineGroupService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineGroupServiceImpl implements MedicineGroupService {

    private final MedicineGroupRepository medicineGroupRepository;

    public MedicineGroupServiceImpl(MedicineGroupRepository medicineGroupRepository) {
        this.medicineGroupRepository = medicineGroupRepository;
    }

    @Override
    public MedicineGroup create(MedicineGroup medicineGroup) {
        if (medicineGroupRepository.existsByGroupName(medicineGroup.getGroupName())) {
            throw new IllegalArgumentException("Group name already exists.");
        }
        return medicineGroupRepository.save(medicineGroup);
    }

    @Override
    public MedicineGroup update(Long id, MedicineGroup updatedGroup) {
        MedicineGroup existing = medicineGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicine group not found."));
        existing.setGroupName(updatedGroup.getGroupName());
        existing.setDescription(updatedGroup.getDescription());
        return medicineGroupRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!medicineGroupRepository.existsById(id)) {
            throw new EntityNotFoundException("Medicine group not found.");
        }
        medicineGroupRepository.deleteById(id);
    }

    @Override
    public Optional<MedicineGroup> getById(Long id) {
        return medicineGroupRepository.findById(id);
    }

    @Override
    public List<MedicineGroup> getAll() {
        return medicineGroupRepository.findAllByOrderByIdAsc();
    }
}