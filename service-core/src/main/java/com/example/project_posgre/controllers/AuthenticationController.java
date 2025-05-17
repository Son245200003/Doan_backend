package com.example.project_posgre.controllers;

import com.example.project_posgre.dtos.requests.LoginRequest;
import com.example.project_posgre.dtos.requests.ResetPasswordDTO;
import com.example.project_posgre.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.refresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.logout(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.forgotPassword(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String secretKey) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.resetPassword(secretKey));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ResetPasswordDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.changePassword(request));
    }

    @GetMapping("/findAllToken")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.getAllToken());
    }
}
