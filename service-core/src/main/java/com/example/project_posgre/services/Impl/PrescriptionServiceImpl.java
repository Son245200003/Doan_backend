package com.example.project_posgre.services.Impl;

import com.example.project_posgre.models.Prescription;
import com.example.project_posgre.repository.PrescriptionRepository;
import com.example.project_posgre.services.PrescriptionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository repository;

    @Override
    public List<Prescription> findAll() {
        return repository.findAll();
    }

    @Override
    public Prescription findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Prescription save(Prescription prescription) {
        prescription.setStatus(Prescription.Status.UNPAID);
        return repository.save(prescription);
    }

    @Override
    public Prescription update(Long id, Prescription prescription) {
        Prescription existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prescription not found"));

        // Gán từng trường, bỏ qua id, createdAt, updatedAt
        existing.setPatient(prescription.getPatient());
        existing.setDoctor(prescription.getDoctor());
        existing.setPrescriptionDate(prescription.getPrescriptionDate());
        existing.setDiagnosis(prescription.getDiagnosis());
        existing.setInstructions(prescription.getInstructions());
        existing.setNotes(prescription.getNotes());
        existing.setStatus(Prescription.Status.UNPAID);
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}