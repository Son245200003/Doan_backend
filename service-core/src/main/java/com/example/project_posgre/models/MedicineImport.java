//package com.example.project_posgre.models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@Table(name = "medicine_imports")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class MedicineImport {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "import_date")
//    private Date importDate;
//
//    private String supplier;
//
//    @Column(name = "total_amount")
//    private BigDecimal totalAmount;
//
//    @Column(columnDefinition = "TEXT")
//    private String notes;
//
//    @Enumerated(EnumType.STRING)
//    private ImportStatus status;
//
//    @OneToMany(mappedBy = "medicineImport", cascade = CascadeType.ALL)
//    private List<MedicineImportDetail> medicineImportDetails;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
//    public enum ImportStatus {
//        PENDING, APPROVED, CANCELLED
//    }
//}
