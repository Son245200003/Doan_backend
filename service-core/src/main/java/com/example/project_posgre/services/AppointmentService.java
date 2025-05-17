package com.example.project_posgre.services;


import com.example.project_posgre.dtos.requests.AppointmentRequestDTO;
import com.example.project_posgre.models.Appointment;
import com.example.project_posgre.models.User;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> getAll();
    Optional<Appointment> getById(Long id);
    Appointment create(AppointmentRequestDTO appointment, User user);
    Appointment update(Long id, AppointmentRequestDTO appointment,User user);
    void delete(Long id);
}