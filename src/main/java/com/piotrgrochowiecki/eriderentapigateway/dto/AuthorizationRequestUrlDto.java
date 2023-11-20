package com.piotrgrochowiecki.eriderentapigateway.dto;

import lombok.Builder;

@Builder
public record AuthorizationRequestUrlDto(String url,
                                         String httpMethod) {

}
