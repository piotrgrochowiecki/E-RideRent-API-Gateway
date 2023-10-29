package com.piotrgrochowiecki.eriderentapigateway.domain.model;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String email,
                                     String accessToken) {

}
