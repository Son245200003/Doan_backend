package com.example.project_posgre.controllers;

import com.example.project_posgre.models.Role;
import com.example.project_posgre.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("")
    public ResponseEntity<Role> addRole(@RequestBody Role role){
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.addRole(role));
    }
    @GetMapping()
    public ResponseEntity<List<Role>> getRoles(){
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.findAllRole());
    }
}
