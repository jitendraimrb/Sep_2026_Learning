package com.practice.demo.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.demo.dto.PaymentSagaRequest;
import com.practice.demo.service.Service1SagaOrchestrator;

@RestController
@RequestMapping("/payments")
public class Service1Controller {

    private final Service1SagaOrchestrator sagaOrchestrator;

    public Service1Controller(Service1SagaOrchestrator sagaOrchestrator) {
        this.sagaOrchestrator = sagaOrchestrator;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startPayment(@RequestBody PaymentSagaRequest request,
                                               @RequestHeader(value = "X-Correlation-Id", required = false)
                                               String correlationIdHeader) {

        String correlationId = correlationIdHeader != null
                ? correlationIdHeader
                : UUID.randomUUID().toString();

        sagaOrchestrator.startPaymentSaga(request, correlationId);

        return ResponseEntity.ok("Payment saga completed, txId=" + request.getTransactionId());
    }
}
