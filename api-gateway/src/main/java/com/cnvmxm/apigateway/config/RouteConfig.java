package com.cnvmxm.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class RouteConfig {

    @Value("${spring.cloud.gateway.routes.discovery-server-url}")
    private String discoveryServerUrl;

    @Value("${spring.cloud.gateway.routes.portfolio-server-url}")
    private String portfolioServerUrl;

    @Bean
    RouteLocator routeLocator(
            RouteLocatorBuilder locatorBuilder
    ) {
        return locatorBuilder.routes()
                .route("portfolio-service", r -> r.path("/api/v1/customers/**")
                        .uri(portfolioServerUrl))
                .route("discovery-service", r -> r.path("/eureka/web")
                        .uri(discoveryServerUrl))
                .build();
    }

}
