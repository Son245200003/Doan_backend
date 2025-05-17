package com.example.project_posgre.repository;

import com.example.project_posgre.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByPhonenumber(String phonenumber);
    Optional<Token> findByAccessToken(String accessToken);
}
