package com.tanle.address.controller;

import com.tanle.address.respone.AddressReponse;
import com.tanle.address.service.AddressSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {
    @Autowired
    private AddressSerivce addressSerivce;

    @GetMapping("/address/{id}")
    public ResponseEntity<List<AddressReponse>> getAddress(@PathVariable int id) {
        List<AddressReponse> addressReponse = addressSerivce.findByEmployeeId(id);
        return ResponseEntity.ok(addressReponse);
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressReponse>> getAllAddress() {
        List<AddressReponse> addressReponse = addressSerivce.findAll();
        return ResponseEntity.ok(addressReponse);
    }

    @PostMapping("/address")
    public ResponseEntity createAddress(@RequestBody List<AddressReponse> addressRequest) {
       addressSerivce.createAddress(addressRequest);
        return ResponseEntity.ok("SUCCESS");
    }
}
