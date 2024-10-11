package com.kirangajul.inventoryservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.kirangajul.inventoryservice.dto.response.TokenValidationResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtValidate {

    private final WebClient.Builder webClientBuilder;

    @Value("${user-service.base-url}")
    private String userServiceBaseUrl; 

    @Autowired
    public JwtValidate(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Boolean validateTokenUserService(String authorizationHeader) {

        String jwtToken = authorizationHeader.replace("Bearer ", "");

        TokenValidationResponse responseToken = webClientBuilder.baseUrl(userServiceBaseUrl + "/api/auth")
                .build()
                .get()
                .uri("/validateToken")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(TokenValidationResponse.class)
                .block();

        return responseToken != null && "Valid token".equals(responseToken.getMessage());

    }

}

