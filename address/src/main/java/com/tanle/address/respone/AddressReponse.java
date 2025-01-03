package com.tanle.address.respone;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AddressReponse {
    private int id;
    private String city;
    private String country;
    private String detail;
}
