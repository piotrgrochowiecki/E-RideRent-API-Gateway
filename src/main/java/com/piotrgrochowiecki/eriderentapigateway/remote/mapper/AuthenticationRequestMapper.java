package com.piotrgrochowiecki.eriderentapigateway.remote.mapper;

import com.piotrgrochowiecki.eriderentapigateway.domain.model.AuthenticationRequest;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationRequestDto;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationRequestMapper {

    public AuthenticationRequest mapToModel(AuthenticationRequestDto requestDto) {
        return AuthenticationRequest.builder()
                .email(requestDto.email())
                .password(requestDto.password())
                .build();
    }

    public AuthenticationRequestDto mapToDto(AuthenticationRequest authenticationRequest) {
        return AuthenticationRequestDto.builder()
                .email(authenticationRequest.email())
                .password(authenticationRequest.password())
                .build();
    }

}
