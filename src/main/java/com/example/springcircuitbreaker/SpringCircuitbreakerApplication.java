package com.example.springcircuitbreaker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringCircuitbreakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCircuitbreakerApplication.class, args);
    }


}
