package com.example.project_posgre.services.Impl;

import com.example.project_posgre.dtos.requests.StaffRequestDTO;
import com.example.project_posgre.models.Attachment;
import com.example.project_posgre.models.Department;
import com.example.project_posgre.models.Staff;
import com.example.project_posgre.models.User;
import com.example.project_posgre.repository.AttachmentRepository;
import com.example.project_posgre.repository.DepartmentRepository;
import com.example.project_posgre.repository.StaffRepository;
import com.example.project_posgre.repository.UserRepository;
import com.example.project_posgre.services.StaffService;
import com.example.project_posgre.utils.FileStorageUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public Staff createStaff(StaffRequestDTO dto) {
        Staff staff = new Staff();
        mapDtoToEntity(dto, staff);
        staff.setStaffCode(generateUniquePatientCode());
        return staffRepository.save(staff);
    }

    @Override
    public Staff updateStaff(Long id, StaffRequestDTO dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found"));
        mapDtoToEntity(dto, staff);
        return staffRepository.save(staff);
    }

    @Override
    public void deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new EntityNotFoundException("Staff not found");
        }
        staffRepository.deleteById(id);
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found"));
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<Staff> getAllDoctor() {
        return staffRepository.findDoctors();
    }

    private String generateUniquePatientCode() {
        int year = java.time.Year.now().getValue();

        long countForYear = staffRepository.countByStaffCodeStartingWith("STF-" + year + "-");
        long nextNumber = countForYear + 1;

        String code = String.format("STF-%d-%03d", year, nextNumber);

        while (staffRepository.existsByStaffCode(code)) {
            nextNumber++;
            code = String.format("STF-%d-%03d", year, nextNumber);
        }

        return code;
    }
    private void mapDtoToEntity(StaffRequestDTO dto, Staff staff) {
        staff.setFullName(dto.getFullName());
        staff.setDateOfBirth(dto.getDateOfBirth());
        staff.setGender(dto.getGender());
        staff.setPosition(dto.getPosition());
        staff.setQualification(dto.getQualification());
        staff.setDegrees(dto.getDegrees());
        staff.setExperience(dto.getExperience());
        staff.setJoinDate(dto.getJoinDate());
        staff.setAddress(dto.getAddress());
        staff.setPhone(dto.getPhone());
        staff.setEmail(dto.getEmail());
        staff.setNotes(dto.getNotes());
        staff.setStatus(dto.getStatus());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            staff.setUser(user);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElse(null);
            staff.setDepartment(department);
        } else {
            staff.setDepartment(null);
        }
    }
    public Attachment uploadAttachment(Long staffId, MultipartFile file) throws IOException {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        String uniqueFilename = FileStorageUtil.storeFile(file);
        String fileUrl = FileStorageUtil.getFileUrl(uniqueFilename);

        Attachment attachment = new Attachment();
        attachment.setStaff(staff);
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setFileSize(file.getSize());
        attachment.setUrl(fileUrl);
        attachment.setUploadedAt(LocalDateTime.now());
        return attachmentRepository.save(attachment);
    }
}
