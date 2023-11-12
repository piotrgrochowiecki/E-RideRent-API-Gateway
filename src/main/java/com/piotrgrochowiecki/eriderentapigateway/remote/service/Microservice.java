package com.piotrgrochowiecki.eriderentapigateway.remote.service;

import lombok.Getter;

@Getter
public enum Microservice {
    USER (8081),
    CAR(8082),
    BOOKING(8083);

    private final int port;

    Microservice(int port) {
        this.port = port;
    }
}
