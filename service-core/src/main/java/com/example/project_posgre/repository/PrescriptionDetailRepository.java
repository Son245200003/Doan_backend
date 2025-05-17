package com.example.project_posgre.repository;


import com.example.project_posgre.models.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, Long> {
}