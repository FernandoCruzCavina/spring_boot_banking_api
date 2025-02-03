package org.example.bankup.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.constants.TransactionType;
import org.example.bankup.entity.Account;

import java.sql.Timestamp;

@Data
@Getter
@AllArgsConstructor
public class ViewTransactionDto {

    private Account fromAccount;

    private Account toAccount;

    private double amount;

    private Timestamp createdTransactionDate;

    private Timestamp completedTransactionDate;

    private TransactionType transactionType;

    private TransactionStatus status;
}
