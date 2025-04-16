package org.example.bankup.dto.customer;

public record UpdateCustomerDto (
        String name,
        String email,
        String password,
        String country,
        String state,
        String city
){}
