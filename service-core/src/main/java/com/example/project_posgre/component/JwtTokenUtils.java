package com.example.project_posgre.component;

import com.example.project_posgre.exception.InvalidParamException;
import com.example.project_posgre.exception.NotFoundException;
import com.example.project_posgre.models.Token;
import com.example.project_posgre.models.User;
import com.example.project_posgre.repository.TokenRepository;
import com.example.project_posgre.services.TokenService;
import com.example.project_posgre.utils.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

import static com.example.project_posgre.utils.TokenType.*;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.refreshKey}")
    private String refreshKey;
    @Value("${jwt.resetKey}")
    private String resetKey;
    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;
    private final TokenRepository tokenRepository;

    public String generateToken(User user){
        Map<String,Object> claims=new HashMap<>();
        claims.put("phoneNumber",user.getPhoneNumber());
        claims.put("userId",user.getId());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+ 1000L *expiration))
                    .signWith(getKey(ACCESS_TOKEN),SignatureAlgorithm.HS256)
                    .compact();
        }catch (Exception e){
            throw  new InvalidParamException("ko tạo đc jwt token"+e.getMessage());
        }
    }
    public String generateRefreshToken(User user){
        Map<String,Object> claims=new HashMap<>();
        claims.put("phoneNumber",user.getPhoneNumber());
        claims.put("userId",user.getId());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+expirationRefreshToken*1000L))
                    .signWith(getKey(REFRESH_TOKEN),SignatureAlgorithm.HS256)
                    .compact();
        }catch (Exception e){
            throw  new InvalidParamException("ko tạo đc jwt token"+e.getMessage());
        }
    }
    public String generateResetToken(User user){
        Map<String,Object> claims=new HashMap<>();
        claims.put("phoneNumber",user.getPhoneNumber());
        claims.put("userId",user.getId());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+60*60*1000L))
                    .signWith(getKey(RESET_TOKEN),SignatureAlgorithm.HS256)
                    .compact();
        }catch (Exception e){
            throw  new InvalidParamException("ko tạo đc jwt token"+e.getMessage());
        }
    }
    private Key getKey(TokenType type){

        byte[] keyBytes;
        if (type == ACCESS_TOKEN) {
            keyBytes = Decoders.BASE64.decode(secretKey);  // Sử dụng secretKey cho ACCESS_TOKEN
        } else if (type == REFRESH_TOKEN) {
            keyBytes = Decoders.BASE64.decode(refreshKey);  // Sử dụng refreshKey cho REFRESH_TOKEN
        } else if (type == RESET_TOKEN) {
            keyBytes = Decoders.BASE64.decode(resetKey);  // Sử dụng refreshKey cho REFRESH_TOKEN
        } else{
            throw new IllegalArgumentException("Invalid token type");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token,TokenType type){
        return Jwts.parserBuilder()
                .setSigningKey(getKey(type))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public  <T> T extractClaim(String token,TokenType type, Function<Claims,T> claimsResolver){
        final Claims claims= this.extractAllClaims(token,type);
        return claimsResolver.apply(claims);
    }
    public boolean isTokenExpired(String token,TokenType type){
        Date expirationDate=this.extractClaim(token,type,Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhonenumber(String token,TokenType type){
        return extractClaim(token,type,Claims::getSubject);
    }
    public boolean validateToken(String token, User userDetails,TokenType type){
            String phoneNumber = extractPhonenumber(token,type);
//            Optional<Token> existToken=tokenRepository.findByAccessToken(token);
//            if(existToken.isEmpty()){
//                throw new NotFoundException("Token da het han hoac da cap nhat trong database");
//            }
            return (phoneNumber.equals(userDetails.getUsername()))
                    && !isTokenExpired(token,type);

    }
}
