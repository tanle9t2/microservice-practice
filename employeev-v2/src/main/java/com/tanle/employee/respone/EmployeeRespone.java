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

    public void setAddressReponses(List<AddressReponse> addressReponses) {
        this.addressReponses = addressReponses;
    }

    public List<AddressReponse> getAddressReponses() {
        return addressReponses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }
}
