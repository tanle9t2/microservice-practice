package com.tanle.employee.feignClient;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

//@LoadBalancerClient(value = "address-service", configuration = MyCustomLoadBalancerConfiguration.class)
// this annotation allow custom with my custom load balancer . Default: Round Robin
@LoadBalancerClient(value = "address-service")
public class AddressServiceLoadBalancer {

    @LoadBalanced
    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
