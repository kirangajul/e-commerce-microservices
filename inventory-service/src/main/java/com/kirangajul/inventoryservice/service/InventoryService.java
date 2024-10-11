package com.kirangajul.inventoryservice.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.kirangajul.inventoryservice.dto.request.TokenValidationRequest;
import com.kirangajul.inventoryservice.dto.response.InventoryResponse;
import com.kirangajul.inventoryservice.dto.response.TokenValidationResponse;
import com.kirangajul.inventoryservice.repository.InventoryRepository;

import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;

    @Autowired
    private final WebClient.Builder webClientBuilder;

    @Value("${user-service.base-url}")
    private String userServiceBaseUrl;

    // get Token in -> user-service
    public Mono<String> getTokenFromUserService() {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/manager/token")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> requestTokenValidation(String accessToken) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/api/auth/validateToken")
                .body(BodyInserters.fromValue(new TokenValidationRequest(accessToken)))
                .retrieve()
                .bodyToMono(TokenValidationResponse.class)
                .map(TokenValidationResponse::getMessage);
    }

    public String getTokenUserService(String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");

        String responseToken = webClientBuilder.baseUrl(userServiceBaseUrl + "/api/manager")
                .build()    
                .get()
                .uri("/token")  
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)    // title
                .retrieve() // call HTTP and return ClientResponse
                .bodyToMono(String.class) // transaction content ClientResponse for Mono.
                .block();   

        return responseToken;
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> productName) {
        log.info("Checking Inventory");
        return inventoryRepository.findByProductNameIn(productName)
                .stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .productName(inventory.getProductName())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }

}
