package com.tanle.employee.feignClient;

import com.tanle.employee.respone.AddressReponse;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "address-service", path = "/address-app/api/v1/")
@RibbonClient(name = "address-service")
public interface AddressClient {
    @GetMapping("/address/{id}")
    ResponseEntity<List<AddressReponse>> getAddressByEmloyeeId(@PathVariable int id);
}
