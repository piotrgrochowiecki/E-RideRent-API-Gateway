package com.piotrgrochowiecki.eriderentapigateway.remote.client;

import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationServiceClient {

    ResponseEntity<?> authenticate(AuthenticationRequestDto requestDto);

}
