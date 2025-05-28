package com.example.project_posgre.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_code")
    private String patientCode; // Mã bệnh nhân

    @Column(name = "full_name", nullable = false)
    private String fullName; // Họ và tên

    @Column(name = "date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth; // Ngày sinh

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender; // Giới tính

    @Column(name = "identity_card")
    private String identityCard; // Số CMND/CCCD

    @Column(name = "insurance_number")
    private String insuranceNumber; // Số thẻ BHYT

    @Column(name = "address")
    private String address; // Địa chỉ

    @Column(name = "phone")
    private String phone; // Số điện thoại

    @Column(name = "email")
    private String email; // Email

    @Column(name = "blood_type")
    private String bloodType; // Nhóm máu

//    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies; // Dị ứng thuốc

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // Ghi chú

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Ngày tạo

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Ngày cập nhật

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
