package org.example.bankup.dto.loan;

import lombok.Data;
import org.example.bankup.entity.Account;

import java.sql.Timestamp;

@Data
public class LoanDto {

    private int id;
    private double amount;
    private Timestamp date;
    private Account account;
}
