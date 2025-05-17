package com.example.project_posgre.services.Impl;

import com.example.project_posgre.exception.NotFoundException;
import com.example.project_posgre.models.RedisToken;
import com.example.project_posgre.repository.RedisTokenRepository;
import com.example.project_posgre.services.RedisTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisTokenServiceImpl implements RedisTokenService {

    private final RedisTokenRepository redisTokenRepository;
    public String save(RedisToken redisToken){
        redisTokenRepository.save(redisToken);
        return redisToken.getId();
    }

    public void deleteToken(String id){
        redisTokenRepository.deleteById(id);
    }

    @Override
    public RedisToken getTokenById(String id) {
        return redisTokenRepository.findById(id).orElseThrow(() -> new NotFoundException("Token not found with id: "+id));
    }

    @Override
    public List<RedisToken> getAllToken() {
        return (List<RedisToken>)redisTokenRepository.findAll();
    }
}
