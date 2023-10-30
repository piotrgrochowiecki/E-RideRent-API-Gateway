package com.piotrgrochowiecki.eriderentapigateway.remote.controller;

import com.piotrgrochowiecki.eriderentapigateway.remote.client.AuthenticationServiceClient;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/gateway")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceClient client;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequestDto requestDto) {
        return client.authenticate(requestDto);
    }

}
