package com.example.project_posgre.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "prescription_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription; // Khóa ngoại với bảng Prescription

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine; // Khóa ngoại với bảng Medicine

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // Số lượng

    @Column(name = "dosage")
    private String dosage; // Liều dùng

    @Column(name = "usage", columnDefinition = "TEXT")
    private String usage; // Cách dùng

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // Ghi chú

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Ngày tạo

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Ngày cập nhật
}
