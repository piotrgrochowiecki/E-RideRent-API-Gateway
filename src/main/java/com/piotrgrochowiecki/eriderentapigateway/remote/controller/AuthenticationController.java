package com.piotrgrochowiecki.eriderentapigateway.remote.controller;

import com.piotrgrochowiecki.eriderentapigateway.remote.client.AuthenticationServiceClient;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/gateway")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceClient client;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        return client.authenticate(authorizationHeader);
    }

}
