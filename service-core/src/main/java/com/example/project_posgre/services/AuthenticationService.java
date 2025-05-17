package com.example.project_posgre.services;

import com.example.project_posgre.dtos.requests.LoginRequest;
import com.example.project_posgre.dtos.reponses.LoginResponse;
import com.example.project_posgre.dtos.requests.ResetPasswordDTO;
import com.example.project_posgre.models.RedisToken;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AuthenticationService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refresh(HttpServletRequest request);

    String logout(HttpServletRequest request);
    public String forgotPassword(String email);
    public String resetPassword(String secretKey);
    public String changePassword(ResetPasswordDTO request);
    List<RedisToken> getAllToken();
}
