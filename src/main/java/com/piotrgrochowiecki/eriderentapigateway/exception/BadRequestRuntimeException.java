package com.piotrgrochowiecki.eriderentapigateway.exception;

public class BadRequestRuntimeException extends RuntimeException {

    public BadRequestRuntimeException(String message) {
        super(message);
    }

}
