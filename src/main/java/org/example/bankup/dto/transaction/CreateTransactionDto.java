package org.example.bankup.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.constants.TransactionType;
import org.example.bankup.entity.Account;

import java.sql.Timestamp;

@Data
@Getter
@Builder
@AllArgsConstructor
public class CreateTransactionDto {

    private long fromAccount;

    private long toAccount;

    //private Timestamp createdTransactionDate;

    //private Timestamp completedTransactionDate;

    private double amount;

    //private TransactionType transactionType;

    //private TransactionStatus status;

}
