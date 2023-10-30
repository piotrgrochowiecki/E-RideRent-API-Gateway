package com.piotrgrochowiecki.eriderentapigateway.remote.client;

import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationRequestDto;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationResponseDto;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.RuntimeExceptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceClientImpl implements AuthenticationServiceClient {

    private final WebClient authenticationClient;

    @Value("${url.identityProvider}")
    private String IDENTITY_PROVIDER_URL;

    @Value("${url.identityProvider.authentication}")
    private String IDENTITY_PROVIDER_AUTHENTICATE_ENDPOINT;

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequestDto requestDto) {
        String endpoint = IDENTITY_PROVIDER_URL +
                          IDENTITY_PROVIDER_AUTHENTICATE_ENDPOINT;

        return authenticationClient.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(AuthenticationResponseDto.class)
                                .map(ResponseEntity::ok);
                    } else {
                        return clientResponse.bodyToMono(RuntimeExceptionDto.class)
                                .map(errorResponse -> ResponseEntity.status(clientResponse.statusCode())
                                        .body(new RuntimeExceptionDto(errorResponse.message(), LocalDateTime.now())));
                    }
                })
                .block();
    }

}
