# Circuit-Breaker
##목차   
###
* [Step00] 참조문서
* [Step10] circuit breaker 란?
* [Step20] Why resilience4j?
* [Step30] dependency를 설정하기
* [Step40] circuit breaker 설정해보기
* [Step50] circuitBreaker 확인해보기
* [step60] circuitbreaker의 옵션 살펴보기
* [step70] 부가적인 UI 설정 (acturator, prometheus, grafana)


### [Step00] 참조문서
> 공식   
https://resilience4j.readme.io/docs   
> 
> 공식 예제 git   
> https://github.com/resilience4j/resilience4j-spring-boot2-demo

### [Step10] circuit breaker 란?
![circuit_braker](./doc/circuit_2.png)

>회로차단기 형태로 장애가 전파되는 현상을 막는 기능을 한다.

### [Step20] Why resilience4j?
>https://resilience4j.readme.io/docs/comparison-to-netflix-hystrix   
> 
>넷플릭스의 hystrix에서 발전하여 만들어졌다고 한다. hystirx은 공식적으로 지원을 중단한다고 밝혀
resilience4j를 선택하게 되었다.

### [Step30] dependency를 설정하기
> build.gradle 참조
> 

### [Step40] circuit breaker 설정해보기
> 1.package create   
> >controller, service   
> 
> 2.service package 에 Service.java interface 생성

```java
package com.example.springcircuitbreaker.service;

public interface Service {

    String failure();

    String success();

}
```

> 3.service package 에 BackendASerivce.java class 생성

```java
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
```
> 4.controller package 에 BackendAController.java class 생성
```java
package com.example.springcircuitbreaker.controller;

import com.example.springcircuitbreaker.service.BackendASerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/backendA")
public class BackendAController {

    @Autowired
    BackendASerivce businessAService;

    @GetMapping("failure")
    public String failure(){
        return businessAService.failure();
    }

    @GetMapping("success")
    public String success(){
        return businessAService.success();
    }
}
```

>5.서비스 확인해보기
> 
> http://localhost:8282/backendA/failure 요청
> 
> ![failure](./doc/success_test.PNG)
> 
> http://localhost:8282/backendA/success 요청
![success](./doc/failure_test.PNG)
> 
> **6.circuit-breaker 옵션설정**
> 
> 
>7.actuator 확인하기   
> build.gradle 에 actuator 추가 시 자동적으로 /actuator url을 제공해준다.
> > implementation 'org.springframework.boot:spring-boot-starter-actuator'
![actuator_log](./doc/actuator_log.PNG)
>
> circuit-breaker에 대한 actuator도 제공받으려면 application.properties 에 추가 설정

```
management.endpoints.web.exposure.include=* 
management.endpoint.health.show-details=always
management.health.circuitbreakers.enabled=true

resilience4j.circuitbreaker.configs.default.registerHealthIndicator=true
resilience4j.circuitbreaker.instances.backendA.baseConfig=default
```

> 옵션설명   
> management.endpoints.web.exposure.include=*   
> > actuator 설정으로 부가적인 endPoint에 대한 제공을 받는 설정    
> > keyName들 배열로도 설정 가능 circuitbreakers,circuitbreakers-name   
> 
> resilience4j.circuitbreaker.configs.default.registerHealthIndicator=true   
> > actuator 에 circuitbreakr의 endPoint를 제공하겠다는 설정

# [Step50] circuitBreaker 확인해보기

