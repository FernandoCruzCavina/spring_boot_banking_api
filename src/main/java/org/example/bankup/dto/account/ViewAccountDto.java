package org.example.bankup.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.AccountType;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
@Getter
public class ViewAccountDto {

    private double balance;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private Timestamp createdAt;
}
