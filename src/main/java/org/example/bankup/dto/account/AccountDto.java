package org.example.bankup.dto.account;

import java.sql.Timestamp;

public record AccountDto(
        long accountId,
        long customerId,
        double balance,
        double creditLimit,
        String accountRole,
        Timestamp createdAt
){}
