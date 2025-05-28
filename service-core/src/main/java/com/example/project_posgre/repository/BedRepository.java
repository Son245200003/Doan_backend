package com.example.project_posgre.repository;

import com.example.project_posgre.models.Bed;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByRoomId(Long roomId);
    Long countByRoomId(Long roomId);
    List<Bed> findByRoomId(Long roomId, Sort sort);
    List<Bed> findByStatus(Bed.BedStatus status);
    List<Bed> findAllByOrderByIdAsc();
}