package com.example.project_posgre.controllers;

import com.example.project_posgre.dtos.requests.AppointmentRequestDTO;
import com.example.project_posgre.models.Appointment;
import com.example.project_posgre.models.User;
import com.example.project_posgre.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Appointment> create(@RequestBody AppointmentRequestDTO appointment, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.create(appointment,user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Appointment> update(@PathVariable Long id, @RequestBody AppointmentRequestDTO appointment, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.update(id, appointment , user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
