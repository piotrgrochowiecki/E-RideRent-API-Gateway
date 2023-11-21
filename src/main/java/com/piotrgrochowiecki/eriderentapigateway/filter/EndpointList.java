package com.piotrgrochowiecki.eriderentapigateway.filter;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class EndpointList {

    @Value("${url.identityProvider}")
    private String IDENTITY_PROVIDER_HOST;

    @Value("${url.identityProvider.authentication}")
    private String IDENTITY_PROVIDER_AUTHENTICATE_ENDPOINT;

    @Value("${url.userManagement}")
    private String USER_MANAGEMENT_HOST;

    @Value("${url.userManagement.user}")
    private String USER_MANAGEMENT_URL;

    @Value("${url.userManagement.user.create}")
    private String USER_MANAGEMENT_URL_CREATE;

    private final List<Endpoint> anonymousAllowedEndpoints = new ArrayList<>();

    @PostConstruct
    public void init() {
        Endpoint authenticateEndpoint = new Endpoint("api/gateway/authenticate", HttpMethod.POST);
        Endpoint createUserEndpoint = new Endpoint( USER_MANAGEMENT_URL + USER_MANAGEMENT_URL_CREATE, HttpMethod.POST);
        anonymousAllowedEndpoints.add(authenticateEndpoint);
        anonymousAllowedEndpoints.add(createUserEndpoint);
    }

}
