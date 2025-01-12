package com.tanle.address.service;

import com.tanle.address.entity.Address;
import com.tanle.address.rep.AddresRepo;
import com.tanle.address.respone.AddressReponse;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressSerivce {
    @Autowired
    private AddresRepo addresRepo;
    @Autowired
    private ModelMapper modelMapper;

    public List<AddressReponse> findByEmployeeId(int employeeId) {
        List<Address> address = addresRepo.findByEmployeeId(employeeId);
        List<AddressReponse> addressReponses = address.stream()
                .map(a -> modelMapper.map(a, AddressReponse.class))
                .collect(Collectors.toList());
        return addressReponses;
    }

    public List<AddressReponse> findAll() {
        List<Address> address = addresRepo.findAll();
        List<AddressReponse> addressReponses = address.stream()
                .map(a -> modelMapper.map(a, AddressReponse.class))
                .collect(Collectors.toList());
        return addressReponses;
    }
        public void createAddress(List<AddressReponse> addressRequest) {
            List<Address> addresses = List.of(modelMapper.map(addressRequest, Address[].class));
            addresRepo.saveAll(addresses);
        }
}
