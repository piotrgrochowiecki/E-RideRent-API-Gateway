package com.piotrgrochowiecki.eriderentapigateway.helper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class MicroserviceHostProvider {

    @Value("${url.carInventory}")
    private String CAR_INVENTORY_HOST;

    @Value("${url.userManagement}")
    private String USER_MANAGEMENT_HOST;

    @Value("${url.identityProvider}")
    private String IDENTITY_PROVIDER_HOST;

    @Value("${url.bookingManagement}")
    private String BOOKING_MANAGEMENT_HOST;

    private final Map<String, String> microserviceHostMap = new HashMap<>();

    @PostConstruct
    public void init() {
        microserviceHostMap.put("car", CAR_INVENTORY_HOST);
        microserviceHostMap.put("booking", BOOKING_MANAGEMENT_HOST);
        microserviceHostMap.put("user", USER_MANAGEMENT_HOST);
        microserviceHostMap.put("authentication", IDENTITY_PROVIDER_HOST);
    }

}
