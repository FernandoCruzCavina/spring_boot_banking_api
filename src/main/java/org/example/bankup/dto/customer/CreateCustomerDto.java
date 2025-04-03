package org.example.bankup.dto.customer;

import org.example.bankup.constants.CustomerRole;

public record CreateCustomerDto(
        String name,
        String mail,
        String password,
        String country,
        String state,
        String city,
        CustomerRole customerRole
){}
