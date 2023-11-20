package com.piotrgrochowiecki.eriderentapigateway.service;

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

        //TODO poprawić obsługę wyjątków (np. przy próbie stworzenia istniejącego usera)

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
        //TODO stworzyć klasę MicroserviceWrapper, która raz stworzy i dostarczy mapę (lub pasujący adres)
        // chodzi o nie tworzenie mapy za każdym razem
        microserviceMap.put("car", Microservice.CAR);
        microserviceMap.put("booking", Microservice.BOOKING);
        microserviceMap.put("user", Microservice.USER);
        microserviceMap.put("authentication", Microservice.AUTHENTICATION);

        for(Map.Entry<String, Microservice> entry : microserviceMap.entrySet()) {
            if(uri.contains(entry.getKey())) {
                return LOCALHOST + entry.getValue()
                        .getPort() + uri;
            }
        }
        return " "; //TODO rzucić wyjącetk i zwrócić 404
    }

}
