package com.piotrgrochowiecki.eriderentapigateway.domain.model;

import lombok.Builder;

@Builder
public record AuthenticationRequest(String email,
                                    String password) {

}
