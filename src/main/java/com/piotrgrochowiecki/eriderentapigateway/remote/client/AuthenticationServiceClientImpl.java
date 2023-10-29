package com.piotrgrochowiecki.eriderentapigateway.remote.client;

import com.piotrgrochowiecki.eriderentapigateway.domain.service.AuthenticationServiceClient;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationRequestDto;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class AuthenticationServiceClientImpl implements AuthenticationServiceClient {

    private final WebClient authenticationClient;

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto) {
        return authenticationClient.post()
                .uri("http://localhost:8080/api/v1/internal/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(AuthenticationResponseDto.class)
                .block();
    }

}
