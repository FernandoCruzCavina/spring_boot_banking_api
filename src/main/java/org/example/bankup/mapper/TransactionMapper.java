package org.example.bankup.mapper;

import org.example.bankup.dto.transaction.ViewTransactionDto;
import org.example.bankup.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    ViewTransactionDto transactionToViewTransactionDto(Transaction transaction);
}
