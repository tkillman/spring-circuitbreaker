package com.example.springcircuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CircuitBreakerTest extends AbstractCircuitBreakerTest{

    @Test
    public void shouldOpenBackendACircuitBreaker() {

        // 실패, 실패율 : 100%, 수행횟수 : 1번
        produceFailure(BACKEND_A);

        // minimumNumberOfCalls 2로 설정되어 있어 최소 2번은 수행해야 통계를 작성하므로 실패율이 100%이더라도 circuit-breaker 가 오픈되지 않음.
        checkHealthStatus(BACKEND_A, CircuitBreaker.State.CLOSED);

        // 성공, 실패율 : 50%, 수행횟수 : 2번
        produceSuccess(BACKEND_A);

        checkHealthStatus(BACKEND_A, CircuitBreaker.State.CLOSED);

        // 실패, 실패율 : 100 * 2/3 = 66%, 수행횟수 3번
        produceFailure(BACKEND_A);

        checkHealthStatus(BACKEND_A, CircuitBreaker.State.OPEN);
    }

    private void produceFailure(String backend) {
        ResponseEntity<String> response = restTemplate.getForEntity("/" + backend + "/failure", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void produceSuccess(String backend) {
        ResponseEntity<String> response = restTemplate.getForEntity("/" + backend + "/success", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
