package com.practice.demo.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LoggingGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();

        log.info("Incoming request from IP={} to path={}", ip, path);

        return chain.filter(exchange)
                .doOnError(ex -> log.warn("Request from IP={} to path={} failed: {}", ip, path, ex.getMessage()));
    }

    @Override
    public int getOrder() {
        return -1; // run early
    }
}
