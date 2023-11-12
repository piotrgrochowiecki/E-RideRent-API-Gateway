package com.piotrgrochowiecki.eriderentapigateway.client;

import org.springframework.http.ResponseEntity;

public interface AuthenticationServiceClient {

    ResponseEntity<?> authenticate(String authenticationHeader);

}
