package org.example.bankup.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.bankup.constants.CustomerRole;

@Data
@Getter
@Builder
@AllArgsConstructor

public class CreateCustomerDto {

    private String name;
    private String mail;
    private String password;
    private String country;
    private String state;
    private String city;
    private CustomerRole customerRole;
}
