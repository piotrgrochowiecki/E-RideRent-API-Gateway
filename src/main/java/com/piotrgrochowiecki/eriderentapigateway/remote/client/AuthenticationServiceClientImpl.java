package com.piotrgrochowiecki.eriderentapigateway.remote.client;

import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationRequestDto;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationResponseDto;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.RuntimeExceptionDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthenticationServiceClientImpl implements AuthenticationServiceClient {

    private final WebClient authenticationClient;

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequestDto requestDto) {
        return authenticationClient.post()
                .uri("http://localhost:8080/api/v1/internal/auth/login")
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
