package com.example.project_posgre.services.Impl;

import com.example.project_posgre.models.Prescription;
import com.example.project_posgre.repository.PrescriptionRepository;
import com.example.project_posgre.services.PrescriptionService;
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
        return repository.save(prescription);
    }

    @Override
    public Prescription update(Long id, Prescription prescription) {
        prescription.setId(id);
        return repository.save(prescription);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}