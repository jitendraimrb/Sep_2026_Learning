package com.practice.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {
	
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

	@GetMapping("/hello")
    public String hello() {
		
		log.info("Now Service-2 Call is coming......");
        return "Hello from Service-2";
    }
}
