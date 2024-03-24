package org.subhankar.apicagateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.subhankar.apicagateway.filter.AuthenticationFilter;

@Configuration
public class AppConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("UserService", r -> r.path("/users/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://UserService"))
                .route("UserService", r -> r.path("/roles/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://UserService"))
                .route("AuthenticationService", r -> r.path("/auth/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://AuthenticationService"))
                .route("JournalService", r -> r.path("/journal/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://JournalService"))
                .build();
    }
}
