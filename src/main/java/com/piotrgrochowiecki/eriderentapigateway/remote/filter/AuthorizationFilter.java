package com.piotrgrochowiecki.eriderentapigateway.remote.filter;

import com.piotrgrochowiecki.eriderentapigateway.remote.client.AuthorizationServiceClient;
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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String accessToken = req.getHeader(HttpHeaders.AUTHORIZATION);
        String url = req.getRequestURI();

        if (url.equals("/api/gateway/authenticate")) {
            chain.doFilter(request, response);
            return;
        }

        ResponseEntity<String> responseEntity = authorizationServiceClient.authorize(accessToken, url);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            chain.doFilter(request, response);
        } else {
            res.sendError(responseEntity.getStatusCode().value());
        }
    }

}
