package com.piotrgrochowiecki.eriderentapigateway.remote.client;

import com.piotrgrochowiecki.eriderentapigateway.remote.dto.RuntimeExceptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceClientImpl implements AuthenticationServiceClient {

    private final WebClient authenticationClient;

    @Value("${url.identityProvider}")
    private String IDENTITY_PROVIDER_URL;

    @Value("${url.identityProvider.authentication}")
    private String IDENTITY_PROVIDER_AUTHENTICATE_ENDPOINT;

    private final String AUTHORIZATION_TOKEN_NOT_FOUND_MESSAGE = "Authorization token not found in the response";

    @Override
    public ResponseEntity<?> authenticate(String authenticationHeader) {
        String endpoint = IDENTITY_PROVIDER_URL +
                          IDENTITY_PROVIDER_AUTHENTICATE_ENDPOINT;

        return authenticationClient.post()
                .uri(endpoint)
                .header(HttpHeaders.AUTHORIZATION, authenticationHeader)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return handleOkResponse(clientResponse);
                    } else {
                        return handleErrorResponse(clientResponse);
                    }
                })
                .block();
    }

    private Mono<ResponseEntity<?>> handleOkResponse(ClientResponse clientResponse) {
        String authToken = clientResponse
                .headers()
                .asHttpHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authToken != null) {
            return Mono
                    .just(ResponseEntity
                                  .ok()
                                  .header(HttpHeaders.AUTHORIZATION, authToken)
                                  .build());
        } else {
            return Mono
                    .just(ResponseEntity
                                  .internalServerError()
                                  .body(new RuntimeExceptionDto(AUTHORIZATION_TOKEN_NOT_FOUND_MESSAGE,
                                                                LocalDateTime.now())));
        }
    }

    private Mono<ResponseEntity<?>> handleErrorResponse(ClientResponse clientResponse) {
        return clientResponse
                .bodyToMono(RuntimeExceptionDto.class)
                .map(errorResponse -> ResponseEntity
                        .status(clientResponse.statusCode())
                        .body(new RuntimeExceptionDto(errorResponse.message(),
                                                      LocalDateTime.now())));
    }
}
