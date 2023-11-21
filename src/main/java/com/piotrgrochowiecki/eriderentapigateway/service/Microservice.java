package com.piotrgrochowiecki.eriderentapigateway.service;

import lombok.Getter;

@Getter
public enum Microservice {
    USER (8081),
    CAR(8082),
    AUTHENTICATION(8080),
    BOOKING(8083); //TODO zamienić post na cały string http://localhost:8080 i przenieść jako zmienne do application.properties

    private final int port;

    Microservice(int port) {
        this.port = port;
    }
}
