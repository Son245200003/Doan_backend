package com.example.project_posgre.repository;

import com.example.project_posgre.models.MedicineGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineGroupRepository extends JpaRepository<MedicineGroup, Long> {
    boolean existsByGroupName(String groupName);
}
