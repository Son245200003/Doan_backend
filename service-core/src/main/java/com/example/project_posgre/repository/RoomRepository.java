package com.example.project_posgre.repository;

import com.example.project_posgre.models.Patient;
import com.example.project_posgre.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByOrderByIdAsc();

}