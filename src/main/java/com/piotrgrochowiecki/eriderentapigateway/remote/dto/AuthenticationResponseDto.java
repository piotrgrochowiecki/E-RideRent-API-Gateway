package com.piotrgrochowiecki.eriderentapigateway.remote.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponseDto(String email,
                                        String accessToken) {

}
