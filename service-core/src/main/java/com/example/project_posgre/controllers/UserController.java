package com.example.project_posgre.controllers;

import com.example.project_posgre.component.JwtTokenUtils;
import com.example.project_posgre.dtos.requests.CreateUserRequest;
import com.example.project_posgre.models.User;
import com.example.project_posgre.dtos.reponses.UserResponse;
import com.example.project_posgre.services.UserService;
import com.example.project_posgre.utils.TokenType;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user")

public class UserController {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest userRequest){
        User user=userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }
    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findUserByCreateAt(@RequestParam("begin")LocalDate begin,
                                                @RequestParam("end")LocalDate end) {
        List<User> users = userService.searchByCreateAt(begin.atStartOfDay(), end.atTime(23, 59, 59));
        List<UserResponse> userResponses = new ArrayList<>();

        // Lặp qua danh sách người dùng và chuyển đổi sang UserResponse
        for (User u : users) {
            UserResponse userResponse = UserResponse.fromUser(u);
            userResponses.add(userResponse);
        }

        // Trả về danh sách UserResponse
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> createUser(@PathVariable Long id, @RequestBody CreateUserRequest userRequest){
        User user=userService.updateUser(id,userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }
    @GetMapping("/getUserJwt")
    public ResponseEntity<?> getUserByJwt(@RequestHeader("Authorization") String authorization){
        final String token = authorization.substring(7);
        Long claims=jwtTokenUtils.extractClaim(token, TokenType.ACCESS_TOKEN, claims1 -> claims1.get("userId",Long.class));
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(claims));
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> activeUser(@PathVariable Long id, @RequestParam Boolean active) {
        userService.blockOrEnable(id,active);

        // Trả về danh sách UserResponse
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAll() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> userResponses = new ArrayList<>();

        // Lặp qua danh sách người dùng và chuyển đổi sang UserResponse
        for (User u : users) {
            UserResponse userResponse = UserResponse.fromUser(u);
            userResponses.add(userResponse);
        }

        // Trả về danh sách UserResponse
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        // Trả về danh sách UserResponse
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
