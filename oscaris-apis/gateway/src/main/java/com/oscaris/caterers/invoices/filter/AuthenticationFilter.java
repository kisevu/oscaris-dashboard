package com.oscaris.caterers.invoices.filter;

import com.oscaris.caterers.invoices.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = null;
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
//                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    throw new RuntimeException("missing authorization header");
//                }

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
                }


                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);
                    String username = jwtUtil.extractUsername(authHeader);
                    List<String> roles = jwtUtil.extractRoles(authHeader);
                    String roleAsStrings = String.join(",", roles);

                    // Extra claims extracted over here...
//                    String userId = String.valueOf(jwtUtil.extractClaim(authHeader, claims -> claims.get("userId")));
                    Object userIdObj = jwtUtil.extractClaim(authHeader, claims -> claims.get("userId"));
                    String userId = userIdObj != null ? userIdObj.toString() : "";


                    String email = jwtUtil.extractClaim(authHeader, claims -> claims.get("email", String.class));

                    request = exchange.getRequest()
                            .mutate()
                            .header("loggedInUser", username)
                            .header("loggedInUserRoles", roleAsStrings)
                            .header("loggedInUserId", userId != null ? userId : "")
                            .header("loggedInUserEmail", email != null ? email : "")
                            .build();

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        });

    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        log.error("Authentication error: {}", err);

        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        String body = String.format("{\"error\": \"%s\"}", err);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    public static class Config {

    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

}
