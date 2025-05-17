package com.example.project_posgre.filter;

import com.example.project_posgre.component.JwtTokenUtils;
import com.example.project_posgre.exception.NotFoundException;
import com.example.project_posgre.models.User;
import com.example.project_posgre.services.UserService;
import com.example.project_posgre.utils.TokenType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilterToken extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("--------------------Pre filter-------------------");
        try {
            final String authorization=request.getHeader("Authorization");
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorize");
            }
            if (authorization != null && authorization.startsWith("Bearer ")) {
                final String token = authorization.substring(7);
    //            log.info(token);
                String phonenumber= jwtTokenUtils.extractPhonenumber(token, TokenType.ACCESS_TOKEN);
                log.info(phonenumber);
                if(phonenumber!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                    User userDetails= (User) userDetailsService.loadUserByUsername(phonenumber);
                    if(jwtTokenUtils.validateToken(token,userDetails,TokenType.ACCESS_TOKEN)){
                        UsernamePasswordAuthenticationToken authenticationToken=
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }

                }

            }
            filterChain.doFilter(request,response);
        } catch (ExpiredJwtException e) {
            log.error("Token đã hết hạn: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token đã hết hạn. Vui lòng đăng nhập lại.");
        } catch (JwtException e) {
            log.error("Token không hợp lệ: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ.");
        }
    }
    private boolean isBypassToken(@Nonnull HttpServletRequest request) {
        final Map<String, List<String>> bypassTokens = new HashMap<>();
        bypassTokens.put(String.format("%s/auth/login", apiPrefix), Arrays.asList("POST"));
        bypassTokens.put(String.format("%s/auth/refresh", apiPrefix), Arrays.asList("POST"));
        bypassTokens.put(String.format("%s/auth/logout", apiPrefix), Arrays.asList("POST"));





        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();
        if (requestPath.startsWith("/"+apiPrefix + "/auth/")) {
            return true;
        }
        for (Map.Entry<String, List<String>> entry : bypassTokens.entrySet()) {
            if (requestPath.contains(entry.getKey()) && entry.getValue().contains(requestMethod)) {
                return true;
            }
        }
        return false;
    }
}
