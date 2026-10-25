package com.spring.config.demo.service;

import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    public boolean checkProductAvailability(int productId) {
        return true;
    }
}