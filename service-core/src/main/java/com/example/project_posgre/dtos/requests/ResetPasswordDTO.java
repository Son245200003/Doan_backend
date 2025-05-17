package com.example.project_posgre.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ResetPasswordDTO  {
    private String secretKey;
    private String password;
    private String confirmPassword;

}
