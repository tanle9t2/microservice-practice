package com.tanle.employee.service;

import com.tanle.employee.entity.Employee;
import com.tanle.employee.repo.EmployeeRep;
import com.tanle.employee.respone.AddressReponse;
import com.tanle.employee.respone.EmployeeRespone;
import jakarta.persistence.EntityNotFoundException;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRep employeeRep;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WebClient webClient;

    public Mono<EmployeeRespone> findById(int id) {
        return employeeRep.findById(id)
                .map(employee -> {
                    EmployeeRespone response = modelMapper.map(employee, EmployeeRespone.class);

                    // Non-blocking web client call for addresses
                    return webClient.get()
                            .uri("/address/" + id)
                            .retrieve()
                            .onStatus(HttpStatusCode::isError, clientResponse ->
                                    clientResponse.bodyToMono(String.class)
                                            .flatMap(errorBody -> Mono.error(new Exception("Error fetching addresses: " + errorBody))))
                            .bodyToFlux(AddressReponse.class)
                            .collectList()
                            .map(addressResponses -> {
                                // Set the address list in the response
                                response.setAddressReponses(Optional.ofNullable(addressResponses).orElse(Collections.emptyList()));
                                return response;
                            });
                }).get();
    }

}
