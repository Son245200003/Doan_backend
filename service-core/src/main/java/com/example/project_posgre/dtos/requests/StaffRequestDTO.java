package com.example.project_posgre.dtos.requests;

import com.example.project_posgre.models.Staff;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class StaffRequestDTO {
    private Long userId;
    private String fullName;
    private Date dateOfBirth;
    private Staff.Gender gender;
    private String identityCard;
    private Long departmentId;
    private Staff.Position position;
    private String qualification;
    private String degrees;
    private Integer experience;
    private Date joinDate;
    private String address;
    private String phone;
    private String email;
    private String notes;
    private Staff.Status status;
}
