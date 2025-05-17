package com.example.project_posgre.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "phonenumber",length = 100,unique = true)
    private String phonenumber;

    @Column(name = "access_token", length = 255)
    private String accessToken;

    @Column(name = "expiration_date")
    private LocalDateTime expiration_date;

    @Column(name = "refresh_token",length = 255)
    private String refreshToken;
    @Column(name = "refresh_expiration_date")
    private LocalDateTime refreshExpirationDate;
}
