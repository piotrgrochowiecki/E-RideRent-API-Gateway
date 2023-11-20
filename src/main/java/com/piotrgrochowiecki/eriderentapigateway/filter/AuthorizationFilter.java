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
        String accessToken = req.getHeader(HttpHeaders.AUTHORIZATION);
        String url = req.getRequestURI();
        String httpMethod = req.getMethod().toUpperCase();

        if (isRequestAllowedToBeAnonymous(req)) {
            chain.doFilter(request, response);
            return;
        }

        if (req.getHeader(HttpHeaders.AUTHORIZATION) == null) {
            res.sendError(403, "Forbidden");
            return;
        }

        ResponseEntity<String> responseEntity = authorizationServiceClient.authorize(accessToken, url, httpMethod);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            chain.doFilter(request, response);
        } else {
            res.sendError(responseEntity.getStatusCode().value());
        }
    }

    private boolean isRequestAllowedToBeAnonymous(HttpServletRequest req) {
        return endpointList.getAnonymousAllowedEndpoints().stream()
                .anyMatch(endpoint -> req.getRequestURI()
                                              .contains(endpoint.url())
                                      && req.getMethod()
                                              .equals(endpoint.httpMethod()
                                                                   .toString()
                                                                   .toUpperCase()));
    }

}
