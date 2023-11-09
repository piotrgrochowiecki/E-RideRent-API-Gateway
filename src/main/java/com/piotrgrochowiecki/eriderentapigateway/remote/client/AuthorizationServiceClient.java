package com.piotrgrochowiecki.eriderentapigateway.remote.client;


public interface AuthorizationServiceClient {

    boolean authorize(String authToken, String url);

}
