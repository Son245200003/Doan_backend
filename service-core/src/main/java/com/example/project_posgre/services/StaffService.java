package com.example.project_posgre.services;

import com.example.project_posgre.dtos.requests.StaffRequestDTO;
import com.example.project_posgre.models.Attachment;
import com.example.project_posgre.models.Staff;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StaffService {
    Staff createStaff(StaffRequestDTO dto);
    Staff updateStaff(Long id, StaffRequestDTO dto);
    void deleteStaff(Long id);
    Staff getStaffById(Long id);
    List<Staff> getAllStaff();
    List<Staff> getAllDoctor();
    Attachment uploadAttachment(Long staffId, MultipartFile file) throws IOException;
}
