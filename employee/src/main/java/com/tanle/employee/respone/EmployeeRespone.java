package com.tanle.employee.respone;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeRespone {
    private int id;
    private String name;
    private String email;
    private String blob;
    private List<AddressReponse> addressReponses;
}
