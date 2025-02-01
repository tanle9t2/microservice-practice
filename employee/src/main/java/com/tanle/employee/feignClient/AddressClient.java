package com.tanle.employee.feignClient;

import com.tanle.employee.respone.AddressReponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@FeignClient(name = "address-service", path = "/address-app/api/v1/")
public interface AddressClient {
    @GetMapping("/address/{id}")
    ResponseEntity<List<AddressReponse>> getAddress(@PathVariable int id);

    @GetMapping("/address")
//    @CircuitBreaker(name = "getAllAddress", fallbackMethod = "handleError")
    ResponseEntity<List<AddressReponse>> getAllAddress();

    default ResponseEntity<String> handleError(Exception e) {
        return new ResponseEntity<>("ADDRESS-SERIVCE DOWN", HttpStatusCode.valueOf(500));
    }

    @PostMapping("/address")
    ResponseEntity createAddress(@RequestBody List<AddressReponse> addressRequest);
}
