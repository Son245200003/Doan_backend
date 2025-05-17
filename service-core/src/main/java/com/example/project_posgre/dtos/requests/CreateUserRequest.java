package com.example.project_posgre.dtos.requests;

import com.example.project_posgre.models.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateUserRequest {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String password;
    private boolean active;
    private Long roleId;
}
