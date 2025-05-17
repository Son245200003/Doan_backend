package com.example.project_posgre.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient; // Khóa ngoại với bảng Patient

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Staff doctor; // Khóa ngoại với bảng Staff (bác sĩ)

    @Column(name = "prescription_date", nullable = false)
    private Date prescriptionDate; // Ngày kê đơn

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis; // Chẩn đoán

    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions; // Hướng dẫn sử dụng

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // Ghi chú

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status; // Trạng thái (PENDING, PROCESSING, DISPENSED, CANCELLED)

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Ngày tạo

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Ngày cập nhật

    public enum Status {
        PENDING, PROCESSING, DISPENSED, CANCELLED
    }
}
