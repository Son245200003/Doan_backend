package com.nhp.cloudgateway.filter;

import com.nhp.cloudgateway.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilterToken implements WebFilter {


    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authorization = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("METHOD : " + exchange.getRequest().getMethod() +
                " REQUEST URL: "+ exchange.getRequest().getURI() +
                " HOST : " + exchange.getRequest().getHeaders().getHost() +
                " X-FORWARDED-FOR : " + exchange.getRequest().getHeaders().getFirst("X-Forwarded-For") +
                " USER-AGENT : " + exchange.getRequest().getHeaders().getFirst("User-Agent")

        );

        // Bypass token kiểm tra các URL cần bỏ qua
        if (isBypassToken(exchange)) {
            return chain.filter(exchange);
        }

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authorization.substring(7);

        return validateToken(token)
                .flatMap(isValid -> {
                    if (isValid) {
                        return chain.filter(exchange);
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                })
                .onErrorResume(e -> {
                    log.error("Token validation error: {}", e.getMessage());
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    return exchange.getResponse().setComplete();
                });
    }

    private Mono<Boolean> validateToken(String token) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.colearn.vn").build();

        return webClient.post()
                .uri("/v1.0/user/authentication")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {})
                .map(apiResponse -> apiResponse.getData() != null)
                .onErrorResume(e -> {
                    log.error("Error during token validation: {}", e.getMessage());
                    return Mono.just(false);
                });
    }

    private boolean isBypassToken(ServerWebExchange exchange) {
        String requestPath = exchange.getRequest().getURI().getPath();
        String requestMethod = exchange.getRequest().getMethod().name();

        // Các route cần bypass
        if (
                requestPath.startsWith("/api/v1/")

//                requestPath.startsWith("/api/v1/cms/question-video") ||
//                requestPath.startsWith("/api/v1/monitor/user-action") ||
//                requestPath.startsWith("/api/v1/cms/related-video") ||
//                requestPath.startsWith("/api/v1/cms/advertisement") ||
//                requestPath.startsWith("/api/v1/cms/setting") ||
//                requestPath.startsWith("/api/v1/monitor/video-tracking") ||
//                requestPath.startsWith("/api/v1/monitor/user-session")
        ) {
            return true;
        }

        Map<String, List<String>> bypassTokens = Map.of(
                String.format("%s/setting", "/api/v1/cms"), List.of("GET")
//                String.format("%s/auth/refresh", apiPrefix), List.of("POST"),
//                String.format("%s/auth/logout", apiPrefix), List.of("POST")
        );

        return bypassTokens.entrySet().stream()
                .anyMatch(entry -> requestPath.contains(entry.getKey()) && entry.getValue().contains(requestMethod));
    }
}
