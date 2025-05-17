package com.example.project_posgre.services;

import com.example.project_posgre.models.Role;

import java.util.List;

public interface RoleService {
    Role addRole(Role role);
    List<Role> findAllRole();
}
