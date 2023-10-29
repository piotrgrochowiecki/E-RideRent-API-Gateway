package com.piotrgrochowiecki.eriderentapigateway.remote.mapper;

import com.piotrgrochowiecki.eriderentapigateway.domain.model.AuthenticationRequest;
import com.piotrgrochowiecki.eriderentapigateway.domain.model.AuthenticationResponse;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationRequestDto;
import com.piotrgrochowiecki.eriderentapigateway.remote.dto.AuthenticationResponseDto;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationResponseMapper {

    public AuthenticationResponse mapToModel(AuthenticationResponseDto responseDto) {
        return AuthenticationResponse.builder()
                .email(responseDto.email())
                .accessToken(responseDto.accessToken())
                .build();
    }

    public AuthenticationResponseDto mapToDto(AuthenticationResponse response) {
        return AuthenticationResponseDto.builder()
                .email(response.email())
                .accessToken(response.accessToken())
                .build();
    }

}
