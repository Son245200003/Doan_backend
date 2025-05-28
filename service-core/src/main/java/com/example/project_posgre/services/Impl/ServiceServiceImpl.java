package com.example.project_posgre.services.Impl;
import com.example.project_posgre.models.Service;
import com.example.project_posgre.repository.ServiceRepository;
import com.example.project_posgre.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Service updateService(Long id, Service updatedService) {
        Service existing = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
        existing.setServiceName(updatedService.getServiceName());
        existing.setServiceType(updatedService.getServiceType());
        existing.setPrice(updatedService.getPrice());
        existing.setDescription(updatedService.getDescription());
        existing.setStatus(updatedService.getStatus());
        return serviceRepository.save(existing);
    }

    @Override
    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Service not found with id: " + id);
        }
        serviceRepository.deleteById(id);
    }

    @Override
    public List<Service> getAllServices() {
        return serviceRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Optional<Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }
}