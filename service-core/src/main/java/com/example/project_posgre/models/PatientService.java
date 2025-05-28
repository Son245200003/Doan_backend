package com.example.project_posgre.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Service service;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.UNPAID; // UNPAID/PAID

    public enum Status {
        UNPAID, PAID
    }
}

