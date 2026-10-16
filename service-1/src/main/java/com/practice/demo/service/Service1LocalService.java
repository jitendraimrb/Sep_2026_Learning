package com.practice.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.practice.demo.dto.PaymentSagaRequest;
import com.practice.demo.entity.Transaction;
import com.practice.demo.repository.TransactionRepository;

@Service
public class Service1LocalService {

    private final TransactionRepository transactionRepository;
    private final Logger log = LoggerFactory.getLogger(Service1LocalService.class);

    public Service1LocalService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void debitCustomerAccount(PaymentSagaRequest request, String correlationId) {
        log.info("Debiting customer account, txId={}, correlationId={}",
                request.getTransactionId(), correlationId);

        Transaction txn = new Transaction();
        txn.setId(request.getTransactionId());
        txn.setCustomerId(request.getCustomerId());
        txn.setAmount(request.getAmount());
        txn.setStatus("DEBITED");

        transactionRepository.save(txn);
    }

    public void reverseDebit(String transactionId, String correlationId) {
        log.info("Reversing debit, txId={}, correlationId={}", transactionId, correlationId);

        Transaction txn = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionId));

        txn.setStatus("REVERSED");
        transactionRepository.save(txn);
    }
}
