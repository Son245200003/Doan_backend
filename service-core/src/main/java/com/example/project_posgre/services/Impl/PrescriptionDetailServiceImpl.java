package com.example.project_posgre.services.Impl;

import com.example.project_posgre.models.PrescriptionDetail;
import com.example.project_posgre.repository.PrescriptionDetailRepository;
import com.example.project_posgre.services.PrescriptionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionDetailServiceImpl implements PrescriptionDetailService {

    @Autowired
    private PrescriptionDetailRepository repository;

    @Override
    public List<PrescriptionDetail> findAll() {
        return repository.findAll();
    }

    @Override
    public PrescriptionDetail findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PrescriptionDetail save(PrescriptionDetail detail) {
        return repository.save(detail);
    }

    @Override
    public PrescriptionDetail update(Long id, PrescriptionDetail detail) {
        detail.setId(id);
        return repository.save(detail);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
