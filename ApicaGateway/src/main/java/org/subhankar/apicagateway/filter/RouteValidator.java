package org.subhankar.apicagateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    private final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/auth/register",
            "/users/new");

    private final List<String> adminApiEndpoints = List.of(
            "/users/adm/",
            "/journal"
            );


    public final Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints
            .stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public final Predicate<ServerHttpRequest> isAdmin = request ->
         adminApiEndpoints
                .stream()
                .anyMatch(uri -> request.getURI().getPath().contains(uri))
    ;
}
