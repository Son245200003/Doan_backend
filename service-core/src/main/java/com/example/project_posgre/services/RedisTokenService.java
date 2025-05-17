package com.example.project_posgre.services;

import com.example.project_posgre.models.RedisToken;

import java.util.List;

public interface RedisTokenService {
    String save(RedisToken redisToken);
     void deleteToken(String id);
     RedisToken getTokenById(String id);
     List<RedisToken> getAllToken();
}
