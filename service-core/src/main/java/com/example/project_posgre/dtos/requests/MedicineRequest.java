package com.example.project_posgre.dtos.requests;

import com.example.project_posgre.models.Medicine;
import com.example.project_posgre.models.MedicineGroup;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class MedicineRequest {
    private String medicineName;

    private Long medicineGroup;

    private String unit;
    private BigDecimal price;

    private Integer stockQuantity;

    private Date expiryDate;

    private String description;

    private Medicine.MedicineStatus status;
}
