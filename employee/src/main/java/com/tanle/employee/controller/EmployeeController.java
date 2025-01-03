package com.tanle.employee.controller;

import com.tanle.employee.repo.EmployeeRep;
import com.tanle.employee.respone.AddressReponse;
import com.tanle.employee.respone.EmployeeRespone;
import com.tanle.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/employee/{id}")
    public ResponseEntity<Mono<EmployeeRespone>> getEmployee(@PathVariable int id) {
        Mono<EmployeeRespone> employeeRespone = employeeService.findById(id);
        return ResponseEntity.ok(employeeRespone);
    }
}
