package com.tanle.employee.feignClient;

import com.tanle.employee.respone.AddressReponse;
import org.springframework.cloud.openfeign.FeignClient;
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
    ResponseEntity<List<AddressReponse>> getAllAddress();

    @PostMapping("/address")
    ResponseEntity createAddress(@RequestBody List<AddressReponse> addressRequest);
}
