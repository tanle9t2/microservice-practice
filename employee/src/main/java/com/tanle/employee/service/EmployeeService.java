package com.tanle.employee.service;

import com.tanle.employee.entity.Employee;
import com.tanle.employee.feignClient.AddressClient;
import com.tanle.employee.repo.EmployeeRep;
import com.tanle.employee.respone.AddressReponse;
import com.tanle.employee.respone.EmployeeRespone;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Table;
import lombok.Setter;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRep employeeRep;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WebClient webClient;
    @Autowired
    private AddressClient addressClient;

    //    public Mono<EmployeeRespone> findById(int id) {
//        return employeeRep.findById(id)
//                .map(employee -> {
//                    EmployeeRespone response = modelMapper.map(employee, EmployeeRespone.class);
//
//                    // Non-blocking web client call for addresses
//                    return webClient.get()
//                            .uri("/address/" + id)
//                            .retrieve()
//                            .onStatus(HttpStatusCode::isError, clientResponse ->
//                                    clientResponse.bodyToMono(String.class)
//                                            .flatMap(errorBody -> Mono.error(new Exception("Error fetching addresses: " + errorBody))))
//                            .bodyToFlux(AddressReponse.class)
//                            .collectList()
//                            .map(addressResponses -> {
//                                // Set the address list in the response
//                                response.setAddressReponses(Optional.ofNullable(addressResponses).orElse(Collections.emptyList()));
//                                return response;
//                            });
//                }).get();
//    }
    public EmployeeRespone findById(int id) {
        Employee employee = employeeRep.findById(id)
                .orElseThrow();

        EmployeeRespone employeeRespone = modelMapper.map(employee, EmployeeRespone.class);
        ResponseEntity<List<AddressReponse>> response = addressClient.getAddress(id);
        if (response.getStatusCode() != HttpStatusCode.valueOf(200)) {
            throw new RuntimeException();
        }
        List<AddressReponse> addressReponses = response.getBody();
        employeeRespone.setAddressReponses(addressReponses);
        return employeeRespone;
    }

    public List<EmployeeRespone> findAll() {
        List<Employee> employees = employeeRep.findAll();
        List<EmployeeRespone> employeeResponeList = List.of(modelMapper.map(employees, EmployeeRespone[].class));
//        List<EmployeeRespone> employeeResponeList = employees.stream()
//                .map(employee -> {
//                    EmployeeRespone employeeRespone = modelMapper.map(employee, EmployeeRespone.class);
//                    ResponseEntity<List<AddressReponse>> response = addressClient.getAddress(employee.getId());
//                    if (response.getStatusCode() != HttpStatusCode.valueOf(200)) {
//                        throw new RuntimeException();
//                    }
//                    List<AddressReponse> addressReponses = response.getBody();
//                    employeeRespone.setAddressReponses(addressReponses);
//                    return employeeRespone;
//                })
//                .collect(Collectors.toList()); with this solution,it will create many request
        ResponseEntity<List<AddressReponse>> response = addressClient.getAllAddress();
        if (response.getStatusCode() != HttpStatusCode.valueOf(200)) {
            throw new RuntimeException();
        }
        List<AddressReponse> addressReponses = response.getBody();
        employeeResponeList.forEach(
                employeeRespone -> {
                    List<AddressReponse> employeeAddress = new ArrayList<>();
                    for (AddressReponse addressReponse : addressReponses) {
                        if (addressReponse.getEmployeeId() == employeeRespone.getId()) {
                            employeeAddress.add(addressReponse);
                        }
                    }
                    employeeRespone.setAddressReponses(employeeAddress);
                }
        );

        return employeeResponeList;
    }


    @Transactional
    public void createEmployee(EmployeeRespone employeeRespone) {
        Employee employee = modelMapper.map(employeeRespone, Employee.class);
        employeeRep.save(employee);
        employeeRespone.getAddressReponses().forEach(addressReponse -> addressReponse.setEmployeeId(employee.getId()));
        ResponseEntity response = addressClient.createAddress(employeeRespone.getAddressReponses());
        if (response.getStatusCode() != HttpStatusCode.valueOf(200))
            throw new RuntimeException();
    }
}
