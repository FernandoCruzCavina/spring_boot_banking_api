package org.example.bankup.dto.transaction;

import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.constants.TransactionType;
import org.example.bankup.entity.Account;

import java.sql.Timestamp;

public record ViewTransactionDto(
        Account fromAccount,
        Account toAccount,
        double amount,
        Timestamp createdTransactionDate,
        Timestamp completedTransactionDate,
        TransactionType transactionType,
        TransactionStatus status
){}
