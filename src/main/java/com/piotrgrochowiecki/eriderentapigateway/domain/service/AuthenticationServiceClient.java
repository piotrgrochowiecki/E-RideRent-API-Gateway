package com.piotrgrochowiecki.eriderentapigateway.domain.service;

import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationRequestDto;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationResponseDto;

public interface AuthenticationServiceClient {

    AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto);

}
