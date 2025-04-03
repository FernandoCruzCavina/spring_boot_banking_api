package org.example.bankup.dto.customer;

import org.example.bankup.constants.CustomerRole;

public record ViewCustomerDto(
        long customerId,
        String name,
        String mail,
        String country,
        String state,
        String city,
        CustomerRole customerRole
){}
