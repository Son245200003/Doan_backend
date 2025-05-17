package com.example.project_posgre.services;


import com.example.project_posgre.models.Prescription;
import java.util.List;

public interface PrescriptionService {
    List<Prescription> findAll();
    Prescription findById(Long id);
    Prescription save(Prescription prescription);
    Prescription update(Long id, Prescription prescription);
    void delete(Long id);
}