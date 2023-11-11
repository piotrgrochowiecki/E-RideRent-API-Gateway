package com.piotrgrochowiecki.eriderentapigateway.remote.client;


import org.springframework.http.ResponseEntity;

public interface AuthorizationServiceClient {

    ResponseEntity<String> authorize(String authToken, String url);

}
