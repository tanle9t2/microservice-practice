package com.tanle.employee.service;

import com.tanle.employee.entity.Employee;
import com.tanle.employee.feignClient.AddressClient;
import com.tanle.employee.repo.EmployeeRep;
import com.tanle.employee.respone.AddressReponse;
import com.tanle.employee.respone.EmployeeRespone;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRep employeeRep;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressClient addressClient;

    public EmployeeRespone findById(int id) {
        EmployeeRespone employee = employeeRep.findById(id)
                .map(em -> modelMapper.map(em, EmployeeRespone.class))
                .get();
        ResponseEntity<List<AddressReponse>> response = addressClient.getAddressByEmloyeeId(id);
        List<AddressReponse> addressReponses = response.getBody();
        employee.setAddressReponses(addressReponses);
//        employee.setAddressReponses(addressReponses);
        return employee;
    }

}
