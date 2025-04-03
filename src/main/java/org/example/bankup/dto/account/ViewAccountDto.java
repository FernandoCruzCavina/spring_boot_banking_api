package org.example.bankup.dto.account;

import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.AccountType;

import java.sql.Timestamp;

public record ViewAccountDto(
        double balance,
        AccountType accountType,
        AccountStatus accountStatus,
        Timestamp createdAt
){}
