package com.example.project_posgre.services;
import com.example.project_posgre.models.PrescriptionDetail;
import java.util.List;

public interface PrescriptionDetailService {
    List<PrescriptionDetail> findAll();
    PrescriptionDetail findById(Long id);
    PrescriptionDetail save(PrescriptionDetail detail);
    PrescriptionDetail update(Long id, PrescriptionDetail detail);
    void delete(Long id);
}