//package com.example.project_posgre.models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//@Table(name = "medicine_histories")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class MedicineHistory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "medicine_id")
//    private Medicine medicine;
//
//    @Column(name = "action_type")
//    @Enumerated(EnumType.STRING)
//    private ActionType actionType;
//
//    private Integer quantity;
//
//    @ManyToOne
//    @JoinColumn(name = "performed_by_id")
//    private User performedBy;
//
//    @Column(name = "performed_date")
//    private Date performedDate;
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
//    public enum ActionType {
//        IMPORT, EXPORT
//    }
//}
