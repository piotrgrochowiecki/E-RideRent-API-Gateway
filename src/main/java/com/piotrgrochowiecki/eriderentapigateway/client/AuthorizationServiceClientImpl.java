package com.piotrgrochowiecki.eriderentapigateway.client;

import com.piotrgrochowiecki.eriderentapigateway.dto.AuthorizationRequestUrlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceClientImpl implements AuthorizationServiceClient {

    private final WebClient webClient;

    @Value("${url.identityProvider}")
    private String IDENTITY_PROVIDER_URL;

    @Value("${url.identityProvider.authorization}")
    private String IDENTITY_PROVIDER_AUTHORIZE_ENDPOINT;

    @Override
    public ResponseEntity<String> authorize(String authToken, String url) {
        String endpoint = IDENTITY_PROVIDER_URL + IDENTITY_PROVIDER_AUTHORIZE_ENDPOINT;
        AuthorizationRequestUrlDto authorizationRequestUrlDto = AuthorizationRequestUrlDto.builder()
                .url(url)
                .build();

        return webClient.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(authorizationRequestUrlDto))
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class))
                .timeout(Duration.ofSeconds(5))
                .block();
    }

}
