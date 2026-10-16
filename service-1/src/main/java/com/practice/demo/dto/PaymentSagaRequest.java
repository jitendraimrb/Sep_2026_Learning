package com.practice.demo.dto;

import java.math.BigDecimal;

public class PaymentSagaRequest {

    private String transactionId;
    private String customerId;
    private BigDecimal amount;

    // constructors
    public PaymentSagaRequest() {}

    public PaymentSagaRequest(String transactionId, String customerId, BigDecimal amount) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
    }

    // getters & setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
