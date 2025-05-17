package com.example.project_posgre.repository;

import com.example.project_posgre.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}