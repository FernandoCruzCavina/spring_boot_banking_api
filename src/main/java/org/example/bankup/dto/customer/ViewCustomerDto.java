package org.example.bankup.dto.customer;

import org.example.bankup.constants.CustomerRole;

public record ViewCustomerDto(
        long id,
        String name,
        String email,
        String country,
        String state,
        String city,
        CustomerRole customer_role
){}
