package com.example.project_posgre.models;

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

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Nếu muốn xử lý vòng lặp khi serialize JSON
    private List<PrescriptionDetail> details = new ArrayList<>();

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


    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Ngày tạo

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Ngày cập nhật

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.UNPAID; // UNPAID/PAID

    public enum Status {
        UNPAID, PAID
    }
}
