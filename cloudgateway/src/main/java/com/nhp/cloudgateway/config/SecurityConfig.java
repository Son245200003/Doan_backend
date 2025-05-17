package com.nhp.cloudgateway.config;

import com.nhp.cloudgateway.filter.JwtFilterToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilterToken jwtFilterToken;

//    /**
//     * Khi Cần Sử dụng OAuth2 trước khi vào serivce phía trong
//     * @param http
//     * @return
//     */
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//                .authorizeExchange(exchanges -> exchanges
//                        .anyExchange().authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt());  // Sử dụng OAuth2 Resource Server với JWT token
//        return http.build();
//    }

//    /**
//     * Khi không cần OAuth2 -> việc xác thực sẽ nằm ở trong service dịch vụ
//     * @param http
//     * @return
//     */
@Bean
public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
    return httpSecurity
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
//            .addFilterAt(jwtFilterToken, SecurityWebFiltersOrder.AUTHENTICATION) // Sử dụng JwtFilterToken
            .authorizeExchange(exchange -> exchange
                    .anyExchange().permitAll()
            )

            .build();
}



}