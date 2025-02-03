package org.example.bankup.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginCustomerDto {

    private String mail;
    private String password;
}
