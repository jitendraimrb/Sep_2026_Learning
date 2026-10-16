package com.practice.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transaction {

    @Id
    private String id;

    private String customerId;
    private BigDecimal amount;
    private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    
}
