package com.example.project_posgre.dtos.reponses;

import com.example.project_posgre.models.Role;
import com.example.project_posgre.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    private String address;
    private String password;
    private boolean active;
    @JsonProperty("role")
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public static UserResponse fromUser(User user){
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .active(user.isActive())
                .role(user.getRoleId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
