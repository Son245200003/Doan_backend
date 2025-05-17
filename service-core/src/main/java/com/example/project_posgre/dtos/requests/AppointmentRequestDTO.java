package com.example.project_posgre.dtos.requests;

import com.example.project_posgre.models.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {
    private Long patientId;
    private Long doctorId;
    private Long departmentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    private String symptoms;
    private Appointment.Status status;
    private String notes;
    private String phoneNumber;
}
