package com.example.project_posgre.services.Impl;

import com.example.project_posgre.component.JwtTokenUtils;
import com.example.project_posgre.exception.InvalidParamException;
import com.example.project_posgre.exception.NotFoundException;
import com.example.project_posgre.models.Token;
import com.example.project_posgre.repository.TokenRepository;
import com.example.project_posgre.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    public long save(Token token) {
        Optional<Token> optionalToken = tokenRepository.findByPhonenumber(token.getPhonenumber());
        if (optionalToken.isPresent()) {
            Token exisToken = optionalToken.get();
            exisToken.setAccessToken(token.getAccessToken());
            exisToken.setRefreshToken(token.getRefreshToken());
            exisToken.setExpiration_date(token.getExpiration_date());
            exisToken.setRefreshExpirationDate(token.getRefreshExpirationDate());
            tokenRepository.save(exisToken);
            System.out.println("update");
            return exisToken.getId();
        } else {
            tokenRepository.save(token);
            System.out.println("new");
            return token.getId();
        }
    }

    @Override
    public String delete(Token token) {
        tokenRepository.delete(token);
       return "OK";
    }

    @Override
    public Token findByPhonenumber(String phonenumber) {
        return tokenRepository.findByPhonenumber(phonenumber).orElseThrow(() -> new NotFoundException("Dont find token with phonenumber : "+phonenumber));
    }

}
