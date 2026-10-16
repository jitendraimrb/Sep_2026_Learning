package com.practice.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.demo.dto.PaymentSagaRequest;
import com.practice.demo.service.LimitService;

@RestController
@RequestMapping("/limits")
public class Service2Controller {

    private final LimitService limitService;

    public Service2Controller(LimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Void> reserve(@RequestBody PaymentSagaRequest request,
                                        @RequestHeader(value = "X-Correlation-Id", required = false)
                                        String correlationId) {

        limitService.reserveLimit(request, correlationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/release")
    public ResponseEntity<Void> release(@RequestBody PaymentSagaRequest request,
                                        @RequestHeader(value = "X-Correlation-Id", required = false)
                                        String correlationId) {

        limitService.releaseLimit(request, correlationId);
        return ResponseEntity.ok().build();
    }
}
