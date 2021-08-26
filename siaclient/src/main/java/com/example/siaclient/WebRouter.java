package com.example.siaclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
class WebRouter {

    @Autowired
    private WebClient h2WebClient;

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route(GET("/test"), this::test);
    }

    private Mono<ServerResponse> test(ServerRequest request) {

        return h2WebClient.get()
                .uri("http://localhost:9192")
                 .exchangeToMono(clientResponse -> ServerResponse.status(clientResponse.statusCode().value())
                 .build());

    }
}