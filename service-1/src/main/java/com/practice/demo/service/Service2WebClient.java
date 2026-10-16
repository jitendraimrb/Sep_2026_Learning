package com.practice.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.practice.demo.dto.PaymentSagaRequest;
import com.practice.demo.exception.BusinessException;
import com.practice.demo.exception.ErrorResponse;

import reactor.core.publisher.Mono;

@Service
public class Service2WebClient {

    private final Logger log = LoggerFactory.getLogger(Service2WebClient.class);

    private final WebClient webClient;

    public Service2WebClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://SERVICE2")   // Eureka service name OR API Gateway route
                .build();
    }

    public void reserveLimit(PaymentSagaRequest request, String correlationId) {

        log.info("Calling Service‑2 to reserve limit, txId={}, correlationId={}",
                request.getTransactionId(), correlationId);

        webClient.post()
                .uri("/limits/reserve")
                .header("X-Correlation-Id", correlationId)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                clientResponse.bodyToMono(ErrorResponse.class)
                        .flatMap(error -> {

                            log.error(
                                    "Service2 Error Code={} Message={}",
                                    error.errorCode(),
                                    error.message());

                            return Mono.error(
                                    new BusinessException(
                                            error.errorCode(),
                                            error.message()));
                        })
        )
                .toBodilessEntity()
                .block();   // SAGA requires blocking call
    }
}
