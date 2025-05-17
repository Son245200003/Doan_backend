package com.example.project_posgre.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String departmentName; // Tên chuyên khoa

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // Mô tả

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status; // Trạng thái (ACTIVE, INACTIVE)

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Ngày tạo

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Ngày cập nhật

    public enum Status {
        ACTIVE, INACTIVE
    }
}