package com.piotrgrochowiecki.eriderentapigateway.filter;
import org.springframework.http.HttpMethod;

record Endpoint(String url,
                       HttpMethod httpMethod) {

}
