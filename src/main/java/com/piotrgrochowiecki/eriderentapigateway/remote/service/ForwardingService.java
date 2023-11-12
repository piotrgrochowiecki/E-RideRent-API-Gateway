package com.piotrgrochowiecki.eriderentapigateway.remote.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ForwardingService {

    private final WebClient webClient;

    private final static String LOCALHOST = "http://localhost:";

    public ResponseEntity<?> forward(HttpServletRequest request, String body) {
        String endpoint = buildEndpoint(request.getRequestURI());
        return webClient.post()
                    .uri(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(body))
                    .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class))
                    .block();
        }

    public ResponseEntity<?> forward(HttpServletRequest request) {
        String endpoint = buildEndpoint(request.getRequestURI());
        return webClient.get()
                    .uri(endpoint)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class))
                    .block();
    }

    private String buildEndpoint(String uri) {
        Map<String, Microservice> microserviceMap = new HashMap<>();
        microserviceMap.put("car", Microservice.CAR);
        microserviceMap.put("booking", Microservice.BOOKING);
        microserviceMap.put("user", Microservice.USER);

        for(Map.Entry<String, Microservice> entry : microserviceMap.entrySet()) {
            if(uri.contains(entry.getKey())) {
                return LOCALHOST + entry.getValue()
                        .getPort() + uri;
            }
        }
        return " ";
    }

}
