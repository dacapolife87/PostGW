package me.hjjang.postgw.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("circuitbreaker_route", r -> r.path("/posts/**")
                        .filters(filter -> filter
                            .circuitBreaker(config -> config.setName("hjjangCircuit")
                            .setFallbackUri("forward:/fallback")
                        )
                    ).uri("lb://post-service"))
                .route(predicateSpec -> predicateSpec
                        .path("/fallback")
                        .uri("http://192.168.0.177:14080/"))
                .build();
    }

}
