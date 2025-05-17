package com.example.project_posgre.repository;

import com.example.project_posgre.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);
    @Query("select o FROM User o WHERE o.active=true AND (:keyword IS NULL OR :keyword ='' OR " +
            "o.fullName LIKE %:keyword% " +
            "OR o.address LIKE %:keyword% " +
            "OR o.phoneNumber LIKE %:keyword% )" +
            "AND o.roleId.name ='USER'")
    Page<User> findAll(@Param("keyword") String keyword, Pageable pageable);

    List<User> findByCreatedAtBetween(LocalDateTime begin, LocalDateTime end);

    Optional<User> findByEmail(String email);
}
