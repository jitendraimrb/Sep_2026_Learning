package com.practice.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_limit")   // FIX: avoid MySQL reserved keyword LIMIT
public class Limit {

    @Id
    private String customerId;

    private BigDecimal available;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }
}
