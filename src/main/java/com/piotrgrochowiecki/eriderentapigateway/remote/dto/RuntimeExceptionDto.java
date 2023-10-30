package com.piotrgrochowiecki.eriderentapigateway.remote.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RuntimeExceptionDto(String message,
                                  LocalDateTime timeStamp) {

}
