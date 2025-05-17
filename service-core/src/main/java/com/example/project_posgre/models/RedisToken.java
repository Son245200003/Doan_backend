package com.example.project_posgre.models;


import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("RedisToken")
public class RedisToken implements Serializable {
    @Id
    private String id;
    private String accessToken;
    private String refreshToken;
    private String resetToken;
    private LocalDateTime resetTokenExpirationDate;
    private LocalDateTime expirationDate;
    private String username;
}
