package com.piotrgrochowiecki.eriderentapigateway.filter;

import com.piotrgrochowiecki.eriderentapigateway.client.AuthorizationServiceClient;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class AuthorizationFilter implements Filter {

    private final AuthorizationServiceClient authorizationServiceClient;
    private final EndpointList endpointList;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (isRequestAllowedToBeAnonymous(req)) {
            chain.doFilter(request, response);
            return;
        }

        if (isAuthorizationHeaderPresent(req)) {
            res.sendError(403, "Forbidden");
            return;
        }

        ResponseEntity<?> responseEntity = authorizationServiceClient.authorize(req);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            chain.doFilter(request, response);
        } else {
            res.setStatus(responseEntity.getStatusCode().value());
            if (responseEntity.getBody() != null) {
                res.setContentType("application/json");
                res.getWriter().write(responseEntity.getBody().toString());
            }
        }
    }

    private boolean isRequestAllowedToBeAnonymous(HttpServletRequest req) {
        return endpointList.getAnonymousAllowedEndpoints().stream()
                .anyMatch(endpoint -> doUrlAndMethodMatch(req, endpoint));
    }

    private boolean doUrlAndMethodMatch(HttpServletRequest req, Endpoint endpoint) {
        return doesMethodMatch(req, endpoint) && doesUrlMatch(req, endpoint);
    }

    private boolean doesUrlMatch(HttpServletRequest req, Endpoint endpoint) {
        return req.getRequestURI().contains(endpoint.url());
    }

    private boolean doesMethodMatch(HttpServletRequest req, Endpoint endpoint) {
        return req.getMethod().equals(endpoint.httpMethod().toString().toUpperCase());
    }

    private boolean isAuthorizationHeaderPresent(HttpServletRequest req) {
        return req.getHeader(HttpHeaders.AUTHORIZATION) == null || req.getHeader(HttpHeaders.AUTHORIZATION).isBlank();
    }

}
