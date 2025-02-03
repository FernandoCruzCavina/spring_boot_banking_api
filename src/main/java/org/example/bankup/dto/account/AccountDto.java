package org.example.bankup.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AccountDto {

    private long account_id;

    private long customer_id;

    private double balance;

    private double credit_limit;

    private String account_role;

    private Timestamp created_at;
}
