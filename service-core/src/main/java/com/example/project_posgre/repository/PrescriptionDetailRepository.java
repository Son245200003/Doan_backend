package com.example.project_posgre.repository;


import com.example.project_posgre.models.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, Long> {
    @Query("SELECT d FROM PrescriptionDetail d WHERE d.prescription.id = :prescriptionId")
    List<PrescriptionDetail> findAllByPrescriptionId(@Param("prescriptionId") Long prescriptionId);

}