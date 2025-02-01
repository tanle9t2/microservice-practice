package com.tanle.employee.controller;

import com.tanle.employee.repo.EmployeeRep;
import com.tanle.employee.respone.AddressReponse;
import com.tanle.employee.respone.EmployeeRespone;
import com.tanle.employee.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    private final String SERVICE_NAME = "employeeService";

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeRespone> getEmployee(@PathVariable int id) {
        EmployeeRespone employeeRespone = employeeService.findById(id);
        return ResponseEntity.ok(employeeRespone);
    }
    public ResponseEntity<String> handleError(Exception e) {
        return new ResponseEntity<>("ADDRESS-SERIVCE DOWN", HttpStatusCode.valueOf(500));
    }

    @GetMapping("/employee")
    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "handleError")
    public ResponseEntity<List<EmployeeRespone>> getAllEmployee() {
        List<EmployeeRespone> employeeResponeList = employeeService.findAll();
        return ResponseEntity.ok(employeeResponeList);
    }

    @PostMapping("/employee")
    public ResponseEntity createEmployee(@RequestBody EmployeeRespone employeeRequest) {
        employeeService.createEmployee(employeeRequest);
        return ResponseEntity.ok("Success");
    }
}
