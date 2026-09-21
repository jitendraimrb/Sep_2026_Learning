package com.practice.demo.service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import reactor.core.publisher.Mono;

@Service
public class Service1Client {

    private final WebClient webClient;
    private final RestTemplate restTemplate;
    AtomicInteger attemptCounter = new AtomicInteger(0);

    public Service1Client(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClient = webClientBuilder.build();
    }

    // WebClient → Modern, reactive, non‑blocking HTTP client
//    public Mono<String> callService2UsingWebClient() {
//        return webClient.get()
//                .uri("http://service2/api/message/hello")   // Eureka service name
//                .retrieve()
//                .bodyToMono(String.class);
//    }

    // WebFlux → Reactive pipeline built on WebClient + Reactor operators
    public Mono<String> callService2UsingWebFlux() {
    	
        return webClient.get()
                .uri("http://service2/api/message/hello")   // Eureka service name
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> "WebFlux Response: " + response);
    }

    // RestTemplate → Legacy, blocking HTTP client
    public String callService2UsingRestTemplate() {
        return restTemplate.getForObject(
                "http://service2/api/message/hello",        // Eureka service name
                String.class
        );
    }
    
    @CircuitBreaker(name = "service2CB", fallbackMethod = "service2Fallback")
    @Retry(name = "service2CB")
    @TimeLimiter(name = "service2CB")
    public CompletableFuture<String> callService2() {
    	return webClient.get()
    	        .uri("http://service2/api/message/hello")
    	        .retrieve()
    	        .bodyToMono(String.class)

    	        // 🔥 Log each retry attempt
    	        .doOnSubscribe(sub -> {
    	            int attempt = attemptCounter.incrementAndGet();
    	            System.out.println("🔁 Retry Attempt #" + attempt + " to call Service2");
    	        })

    	        // 🔥 Log success
    	        .doOnNext(res -> System.out.println("✅ Service2 responded successfully"))

    	        // 🔥 Log error before fallback
    	        .doOnError(err -> System.out.println("❌ Error occurred: " + err.getClass().getSimpleName()))

    	        .timeout(Duration.ofSeconds(2))

    	        // 🔥 Log fallback trigger
    	        .onErrorResume(err -> {
    	            System.out.println("⚠️ Fallback triggered due to: " + err.getClass().getSimpleName());
    	            return Mono.just("Fallback: Service2 is down. Reason: " + err.getClass().getSimpleName());
    	        })

    	        .toFuture();
    }


    // Fallback (still kept for resilience4j, but now rarely needed)
    private CompletableFuture<String> service2Fallback(Throwable t) {
        System.out.println("🛑 CircuitBreaker Fallback executed. Reason: " + t.getClass().getSimpleName());
        return CompletableFuture.completedFuture(
                "Fallback (CB): Service2 is down. Reason: " + t.getClass().getSimpleName()
        );
    }
}
