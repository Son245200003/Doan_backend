package com.example.project_posgre.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient; // Khóa ngoại với bảng Patient

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Staff doctor; // Khóa ngoại với bảng Staff (bác sĩ)

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department; // Khóa ngoại với bảng Department

    @Column(name = "start_time")
    private LocalDateTime startTime; // Thời gian bắt đầu

    @Column(name = "end_time")
    private LocalDateTime endTime; // Thời gian kết thúc

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason; // Lý do khám
    @Column(name = "phone_number", columnDefinition = "TEXT")
    private String phoneNumber;
    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms; // Triệu chứng

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status; // Trạng thái (PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED)

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // Ghi chú

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy; // Người tạo cuộc hẹn

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Ngày tạo

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Ngày cập nhật

    public enum Status {
        PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
    }
}