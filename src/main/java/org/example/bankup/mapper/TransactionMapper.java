package org.example.bankup.mapper;

import org.example.bankup.dto.transaction.ViewTransactionDto;
import org.example.bankup.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    ViewTransactionDto transactionToViewTransactionDto(Transaction transaction);
}
