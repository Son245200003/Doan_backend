package com.example.project_posgre.services.Impl;

import com.example.project_posgre.dtos.requests.AppointmentRequestDTO;
import com.example.project_posgre.models.Appointment;
import com.example.project_posgre.models.User;
import com.example.project_posgre.repository.*;
import com.example.project_posgre.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final StaffRepository staffRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment create(AppointmentRequestDTO dto, User user) {
        Appointment appointment = new Appointment();

        // Gán dữ liệu từ DTO sang entity
        appointment.setStartTime(dto.getStartTime());
        appointment.setEndTime(dto.getEndTime());
        appointment.setReason(dto.getReason());
        appointment.setSymptoms(dto.getSymptoms());
        appointment.setStatus(dto.getStatus());
        appointment.setNotes(dto.getNotes());

        // Lấy các entity liên quan bằng ID
        appointment.setPatient(patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new RuntimeException("Patient not found")));
        appointment.setDoctor(staffRepository.findById(dto.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found")));
        appointment.setDepartment(departmentRepository.findById(dto.getDepartmentId()).orElse(null)); // optional
        appointment.setCreatedBy(user);
        appointment.setPhoneNumber(dto.getPhoneNumber());
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Long id, AppointmentRequestDTO requestDTO,User user) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointment.setPatient(patientRepository.findById(requestDTO.getPatientId()).orElseThrow(() -> new RuntimeException("Patient not found")));
            appointment.setDoctor(staffRepository.findById(requestDTO.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found")));
            appointment.setDepartment(departmentRepository.findById(requestDTO.getDepartmentId()).orElse(null)); // optional
            appointment.setStartTime(requestDTO.getStartTime());
            appointment.setEndTime(requestDTO.getEndTime());
            appointment.setReason(requestDTO.getReason());
            appointment.setSymptoms(requestDTO.getSymptoms());
            appointment.setStatus(requestDTO.getStatus());
            appointment.setNotes(requestDTO.getNotes());
            appointment.setPhoneNumber(requestDTO.getPhoneNumber());

            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
}