package com.LGBank.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	public RouteLocator lgBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(
						p -> p
								.path("/lg-bank/accounts/**")
								.filters(f -> f.rewritePath("/lg-bank/accounts/(?<segment>.*)","/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))

								.uri("lb://ACCOUNTS"))
				.route(
						p -> p
								.path("/lg-bank/cards/**")
								.filters(f -> f.rewritePath("/lg-bank/cards/(?<segment>.*)","/${segment}"))
								.uri("lb:CARDS")
				)
				.route(
						p -> p
								.path("/lg-bank/loans/**")
								.filters(f -> f.rewritePath("/lg-bank/loans/(?<segment>.*)", "${segment}"))
								.uri("lb:LOANS")
				)
				.build();
	}

}
