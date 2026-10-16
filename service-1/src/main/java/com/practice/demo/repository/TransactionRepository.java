package com.practice.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.demo.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
