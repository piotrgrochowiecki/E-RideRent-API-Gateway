package com.piotrgrochowiecki.eriderentapigateway.client;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthorizationServiceClient {

    ResponseEntity<?> authorize(HttpServletRequest request);

}
