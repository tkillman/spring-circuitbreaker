package com.example.springcircuitbreaker.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

@Component(value = "backendAService")
public class BackendASerivce implements Service{

    private static final String BACKEND_A = "backendA";

    @Override
    @CircuitBreaker(name = BACKEND_A)
    public String failure() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
    }

    @Override
    public String success() {
        return "Hello World from backend A";
    }
}
