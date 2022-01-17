package com.example.springcircuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = SpringCircuitbreakerApplication.class)
abstract class SpringCircuitbreakerApplicationTests {

	protected static final String BACKEND_A = "backendA";

	@Autowired
	protected CircuitBreakerRegistry circuitBreakerRegistry;

	@Autowired
	protected TestRestTemplate restTemplate;

	@BeforeEach
	public void setup() {
		// 초기 실행 시 circuit-breaker를 close 상태로 만든다.
		transitionToClosedState(BACKEND_A);
	}

	protected void transitionToOpenState(String circuitBreakerName) {
		CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(circuitBreakerName);
		circuitBreaker.transitionToOpenState();
	}

	protected void transitionToClosedState(String circuitBreakerName) {
		CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(circuitBreakerName);
		circuitBreaker.transitionToClosedState();
	}
}
