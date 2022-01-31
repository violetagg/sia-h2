package com.example.siaclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class Config {

    @Bean
    public HttpClient h2Client() {

        var provider = ConnectionProvider.builder("sia")
                .fifo()
                .maxConnections(100)
                .maxLifeTime(Duration.ofSeconds(20))
                .evictInBackground(Duration.ofSeconds(5))
                .metrics(true)
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
