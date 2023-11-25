package com.piotrgrochowiecki.eriderentapigateway.client;

import com.piotrgrochowiecki.eriderentapigateway.dto.AuthorizationRequestUrlDto;
import com.piotrgrochowiecki.eriderentapigateway.helper.ResponseEntityHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceClientImpl implements AuthorizationServiceClient {

    private final WebClient webClient;
    private final ResponseEntityHelper responseEntityHelper;

    @Value("${url.identityProvider}")
    private String IDENTITY_PROVIDER_URL;

    @Value("${url.identityProvider.authorization}")
    private String IDENTITY_PROVIDER_AUTHORIZE_ENDPOINT;

    @Override
    public ResponseEntity<?> authorize(HttpServletRequest request) {
        String endpoint = IDENTITY_PROVIDER_URL + IDENTITY_PROVIDER_AUTHORIZE_ENDPOINT;
        AuthorizationRequestUrlDto authorizationRequestUrlDto = AuthorizationRequestUrlDto.builder()
                .url(request.getRequestURI())
                .httpMethod(request.getMethod().toUpperCase())
                .build();

        MultiValueMap<String, String> headersMultiValueMap = getAllHeaders(request);

        ResponseEntity<String> responseEntityFromService = webClient.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(authorizationRequestUrlDto))
                .headers(headers -> headers.addAll(headersMultiValueMap))
                .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class))
                .block();
        return responseEntityHelper.transformResponseEntity(responseEntityFromService);
    }

    private MultiValueMap<String, String> getAllHeaders(HttpServletRequest request) {
        Map<String, List<String>> headersFromRequest = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(Function.identity(),
                                          header -> Collections.list(request.getHeaders(header))));
        //remove "host" and "connection" headers as these are restricted and set by a WebClient
        headersFromRequest.remove("host");
        headersFromRequest.remove("connection");

        MultiValueMap<String, String> headersMultiValueMap = new LinkedMultiValueMap<>();
        headersMultiValueMap.putAll(headersFromRequest);
        return headersMultiValueMap;
    }

}
