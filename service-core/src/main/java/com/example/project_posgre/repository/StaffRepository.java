package com.example.project_posgre.repository;

import com.example.project_posgre.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    boolean existsByStaffCode(String staffCode);
    long countByStaffCodeStartingWith(String prefix);
    @Query("SELECT s FROM Staff s WHERE s.position = 'DOCTOR'")
    List<Staff> findDoctors();

}