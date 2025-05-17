package com.example.project_posgre.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at")
//    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
//    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreateorUpdate(){
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }
    @PreUpdate
    protected void onCUpdate(){
        updatedAt=LocalDateTime.now();
    }

}


