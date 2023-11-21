package com.piotrgrochowiecki.eriderentapigateway.filter;

import com.piotrgrochowiecki.eriderentapigateway.client.AuthorizationServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FilterConfig {

    private final AuthorizationServiceClient authorizationServiceClient;
    private final EndpointList endpointList;

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> exposeFilter() {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthorizationFilter(authorizationServiceClient, endpointList));
        registrationBean.addUrlPatterns("/**");

        return registrationBean;
    }

}
