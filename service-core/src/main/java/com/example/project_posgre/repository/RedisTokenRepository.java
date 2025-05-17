package com.example.project_posgre.repository;

import com.example.project_posgre.models.RedisToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisTokenRepository extends CrudRepository<RedisToken,String> {
}
