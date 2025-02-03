package org.example.bankup.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.AccountType;

@Data
@AllArgsConstructor
@Builder
@Getter
public class CreateAccountDto {
    private double balance;
    private long customerId;

}
