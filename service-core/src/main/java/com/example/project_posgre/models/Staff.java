package com.example.project_posgre.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staff_code")
    private String staffCode; // Mã nhân viên

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; // Khóa ngoại với bảng User

    @Column(name = "full_name", nullable = false)
    private String fullName; // Họ và tên

    @Column(name = "date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth; // Ngày sinh

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender; // Giới tính

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department; // Khóa ngoại với bảng Department

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private Position position; // Chức vụ (DOCTOR, NURSE, RECEPTIONIST)

    @Column(name = "qualification")
    private String qualification; // Trình độ chuyên môn

    @Column(name = "degrees")
    private String degrees; // Bằng cấp

    @Column(name = "experience")
    private Integer experience; // Kinh nghiệm (năm)

    @Column(name = "join_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinDate; // Ngày vào làm

    @Column(name = "address")
    private String address; // Địa chỉ

    @Column(name = "phone")
    private String phone; // Số điện thoại

    @Column(name = "email")
    private String email; // Email

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // Ghi chú

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status; // Trạng thái (WORKING, RESIGNED, LEAVE)

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Ngày tạo
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Attachment> attachments = new ArrayList<>();

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Ngày cập nhật

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum Position {
        DOCTOR, NURSE, RECEPTIONIST
    }

    public enum Status {
        WORKING, RESIGNED, LEAVE
    }
}