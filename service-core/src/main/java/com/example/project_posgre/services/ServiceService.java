package com.example.project_posgre.services;

import com.example.project_posgre.models.Service;
import java.util.List;
import java.util.Optional;

public interface ServiceService {
    Service createService(Service service);
    Service updateService(Long id, Service service);
    void deleteService(Long id);
    List<Service> getAllServices();
    Optional<Service> getServiceById(Long id);
}