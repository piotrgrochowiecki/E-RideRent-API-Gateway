package com.piotrgrochowiecki.eriderentapigateway.client;


import jakarta.servlet.ServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthorizationServiceClient {

    ResponseEntity<String> authorize(String authToken, String url, String httpMethod);

}
