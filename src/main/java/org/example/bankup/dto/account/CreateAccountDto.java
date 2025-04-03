package org.example.bankup.dto.account;


import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.AccountType;


public record CreateAccountDto(
        double balance,
        long customerId
){}
