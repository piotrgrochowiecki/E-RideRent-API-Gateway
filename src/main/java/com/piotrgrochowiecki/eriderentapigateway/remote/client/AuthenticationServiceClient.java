package com.piotrgrochowiecki.eriderentapigateway.remote.client;

import org.springframework.http.ResponseEntity;

public interface AuthenticationServiceClient {

    ResponseEntity<?> authenticate(String authenticationHeader);

}
