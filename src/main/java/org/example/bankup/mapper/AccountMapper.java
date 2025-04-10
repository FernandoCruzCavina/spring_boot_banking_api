package org.example.bankup.mapper;

import org.example.bankup.dto.account.ViewAccountDto;
import org.example.bankup.entity.Account;
import org.example.bankup.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "", source = "")
    Account customerToAccount(Customer customer);

    ViewAccountDto accountToViewAccountDto(Account account);
}
