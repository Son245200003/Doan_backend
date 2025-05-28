package com.example.project_posgre.repository;

import com.example.project_posgre.models.Room;
import com.example.project_posgre.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Long> {
    List<Service> findAllByOrderByIdAsc();

}
