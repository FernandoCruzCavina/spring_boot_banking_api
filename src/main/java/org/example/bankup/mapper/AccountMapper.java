package org.example.bankup.mapper;

import org.example.bankup.dto.account.ViewAccountDto;
import org.example.bankup.entity.Account;
import org.example.bankup.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    ViewAccountDto accountToViewAccountDto(Account account);
}
