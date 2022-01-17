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