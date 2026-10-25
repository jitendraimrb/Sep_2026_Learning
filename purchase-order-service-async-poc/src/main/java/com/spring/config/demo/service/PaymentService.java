package com.spring.config.demo.service;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.spring.config.demo.dto.Order;

@Service
public class PaymentService {

	private static final Logger log =
            Logger.getLogger(PaymentService.class.getName());
    public void processPayment(Order order) throws InterruptedException {
        log.info("initiate payment for order " + order.getProductId());
        //call actual payment gateway
        Thread.sleep(2000L);
        log.info("completed payment for order " + order.getProductId());
    }
}