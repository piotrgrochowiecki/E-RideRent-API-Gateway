package com.piotrgrochowiecki.eriderentapigateway.remote.dto;

import lombok.Builder;

@Builder
public record AuthenticationRequestDto(String email,
                                       String password) {

}
