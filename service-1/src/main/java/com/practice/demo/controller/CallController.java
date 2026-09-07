package com.practice.demo.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.demo.service.Service1Client;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/call")
public class CallController {

    @Autowired
    private Service1Client service1Client;

    @GetMapping("/service2-web-client")
    public CompletableFuture<String> callService2() {
        return service1Client.callService2();
    }
    
    
    @GetMapping("/service-2-rest-call")
    public String callService2UsingRestTemplate() {
    	return service1Client.callService2UsingRestTemplate();
    }
    
    // NEW WebFlux endpoint
    @GetMapping("/service2-webflux")
    public Mono<String> callService2UsingWebFlux() {
        return service1Client.callService2UsingWebFlux();
    }
}
