package com.practice.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.practice.demo.dto.PaymentSagaRequest;
import com.practice.demo.entity.Limit;
import com.practice.demo.exception.InsufficientLimitException;
import com.practice.demo.exception.LimitNotFoundException;
import com.practice.demo.repository.LimitRepository;

import jakarta.transaction.Transactional;

@Service
public class LimitService {

    private final LimitRepository limitRepository;
    private final Logger log = LoggerFactory.getLogger(LimitService.class);

    public LimitService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    @Transactional
    public void reserveLimit(PaymentSagaRequest request, String correlationId) {
        log.info("Reserving limit in Service‑2, txId={}, correlationId={}",
                request.getTransactionId(), correlationId);

        Limit limit = limitRepository.findByCustomerId(request.getCustomerId())
                .orElseThrow(() -> new LimitNotFoundException("Limit not found for customer : "
                        + request.getCustomerId()));

        if (limit.getAvailable().compareTo(request.getAmount()) < 0) {
        	 throw new InsufficientLimitException(
        	            "Available Limit="
        	             + limit.getAvailable()
        	             + ", Requested="
        	             + request.getAmount());
        }

        limit.setAvailable(limit.getAvailable().subtract(request.getAmount()));
        limitRepository.save(limit);
    }

    @Transactional
    public void releaseLimit(PaymentSagaRequest request, String correlationId) {
        log.info("Releasing limit in Service‑2, txId={}, correlationId={}",
                request.getTransactionId(), correlationId);

        Limit limit = limitRepository.findByCustomerId(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Limit not found"));

        limit.setAvailable(limit.getAvailable().add(request.getAmount()));
        limitRepository.save(limit);
    }
}
