package com.example.project_posgre.services.Impl;

import com.example.project_posgre.models.Role;
import com.example.project_posgre.repository.RoleRepository;
import com.example.project_posgre.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }
}
