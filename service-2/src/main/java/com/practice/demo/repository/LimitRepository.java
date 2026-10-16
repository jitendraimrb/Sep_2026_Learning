package com.practice.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.demo.entity.Limit;

public interface LimitRepository extends JpaRepository<Limit, String> {

    Optional<Limit> findByCustomerId(String customerId);
}
