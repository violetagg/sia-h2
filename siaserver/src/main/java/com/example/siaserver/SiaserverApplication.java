package com.example.siaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableChannel;
import reactor.netty.DisposableServer;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.server.HttpServer;

import java.time.Duration;

@SpringBootApplication
public class SiaserverApplication {

	public static void main(String[] args) {

		DisposableServer server =
				HttpServer.create()
						.protocol(HttpProtocol.H2C)
						.http2Settings(h2 -> h2.maxConcurrentStreams(20))
						.port(9192)
						.handle((req, res) -> res.sendString(Mono
								.just("sia")
								.delayElement(Duration.ofSeconds(2))))
						.wiretap(true)
						.bindNow();

		server.onDispose()
				.block();
	}

}
