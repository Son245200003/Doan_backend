package com.example.project_posgre.services;

import com.example.project_posgre.dtos.requests.CreateUserRequest;
import com.example.project_posgre.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    User findById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(Long id,CreateUserRequest request);
    void deleteUser(Long id);
    void blockOrEnable(Long userId,Boolean active);
    List<User> getAllUsers();
    List<User> searchByCreateAt(LocalDateTime begin,LocalDateTime end);
}
