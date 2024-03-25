package org.subhankar.apicagateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class AuthenticationFilter implements GatewayFilter {
    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (routeValidator.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange);
            }
            final String token = extractToken(request);
            if(Boolean.TRUE.equals(jwtUtils.isTokenExpired(token))) {
                throw new RuntimeException("Token expired");
            }
            if (routeValidator.isAdmin.test(request)){
                if(!hasRole(token, "ADMIN")) {
                    return onError(exchange);
                }
            }
        }

        return chain.filter(exchange);
    }

    private String extractToken(ServerHttpRequest request) {
        return Objects.requireNonNull(request.getCookies().getFirst("token")).getValue();

    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
    private boolean authMissing(ServerHttpRequest request) {
        return !request.getCookies().containsKey("token");
    }

    private boolean hasRole(String token, String role) {
        List<String> roles = List.of(jwtUtils.extractRoles(token));
        boolean flag = false;
        for (String r : roles) {
            if (r.equals(role)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
