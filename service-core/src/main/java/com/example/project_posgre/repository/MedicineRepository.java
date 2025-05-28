package com.example.project_posgre.repository;

import com.example.project_posgre.models.Medicine;
import com.example.project_posgre.models.MedicineGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findAllByOrderByIdAsc();

}
