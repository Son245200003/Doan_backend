package com.example.project_posgre.services;

import com.example.project_posgre.dtos.requests.StaffRequestDTO;
import com.example.project_posgre.models.Staff;

import java.util.List;

public interface StaffService {
    Staff createStaff(StaffRequestDTO dto);
    Staff updateStaff(Long id, StaffRequestDTO dto);
    void deleteStaff(Long id);
    Staff getStaffById(Long id);
    List<Staff> getAllStaff();
    List<Staff> getAllDoctor();
}
