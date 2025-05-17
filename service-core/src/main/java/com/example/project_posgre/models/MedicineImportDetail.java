//package com.example.project_posgre.models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//@Table(name = "medicine_import_details")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class MedicineImportDetail {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "import_id")
//    private MedicineImport medicineImport;
//
//    @ManyToOne
//    @JoinColumn(name = "medicine_id")
//    private Medicine medicine;
//
//    private Integer quantity;
//
//    @Column(name = "unit_price")
//    private BigDecimal unitPrice;
//
//    private BigDecimal amount;
//
//    @Column(name = "expiry_date")
//    private Date expiryDate;
//
//    @Column(columnDefinition = "TEXT")
//    private String notes;
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
//}
