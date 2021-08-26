package com.example.siaclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class Config {

    @Bean
    public HttpClient h2Client() {

        var provider = ConnectionProvider.builder("sia")
                .maxConnections(100)
                .build();

        return HttpClient.create(provider)
                .protocol(HttpProtocol.H2C)
                .wiretap(true);
    }

    @Bean
    public WebClient h2WebClient(HttpClient h2Client) {

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(h2Client))
                .build();
    }
}
