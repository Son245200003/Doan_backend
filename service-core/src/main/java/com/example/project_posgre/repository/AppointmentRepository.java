package com.example.project_posgre.repository;

import com.example.project_posgre.models.Appointment;
import com.example.project_posgre.models.Department;
import com.example.project_posgre.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.AbstractPipeline;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByOrderByIdAsc();
    List<Appointment> findAllByOrderByCreatedAtDesc(); // Thời gian tạo mới nhất lên đầu
    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId")
    List<Appointment> findByPatientId(@Param("patientId") Long patientId);
}