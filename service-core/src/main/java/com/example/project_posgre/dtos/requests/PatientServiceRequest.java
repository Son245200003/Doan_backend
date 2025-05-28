package com.example.project_posgre.dtos.requests;

import lombok.Data;

import java.util.List;
@Data
public class PatientServiceRequest {
    private Long patientId;
    private List<Long> serviceIds;
}

