package com.piotrgrochowiecki.eriderentapigateway.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class MicroserviceWrapper {

    private final Map<String, Microservice> microserviceMap = new HashMap<>();

    @PostConstruct
    public void init() {
        microserviceMap.put("car", Microservice.CAR);
        microserviceMap.put("booking", Microservice.BOOKING);
        microserviceMap.put("user", Microservice.USER);
        microserviceMap.put("authentication", Microservice.AUTHENTICATION);
    }

}
