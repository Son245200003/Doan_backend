package com.example.project_posgre.dtos.requests;

import com.example.project_posgre.models.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {
    @JsonProperty("phonenumber")
    private String phoneNumber;
    private String password;
//    @JsonProperty("role_id")
//    private Long role;
}
