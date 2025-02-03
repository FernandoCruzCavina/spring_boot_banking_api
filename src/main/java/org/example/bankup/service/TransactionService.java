package org.example.bankup.service;

import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.constants.TransactionType;
import org.example.bankup.dto.transaction.CreateTransactionDto;
import org.example.bankup.dto.transaction.ViewTransactionDto;
import org.example.bankup.entity.Account;
import org.example.bankup.entity.Transaction;
import org.example.bankup.repository.AccountRepository;
import org.example.bankup.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public ViewTransactionDto getTransactionById(long id){
        Optional<Transaction> transaction = transactionRepository.findById(id);

        return transaction.map(t ->
                new ViewTransactionDto(
                        t.getFromAccount(),
                        t.getToAccount(),
                        t.getAmount(),
                        t.getCreatedTransactionDate(),
                        t.getCompletedTransactionDate(),
                        t.getTransactionType(),
                        t.getStatus()
                )).orElse(null);
    }

    public void createPaymentNow(CreateTransactionDto transactionDto) {
        Optional<Account> fromAccount = accountRepository.findFirstByAccountId(transactionDto.getFromAccount());
        Optional<Account> toAccount = accountRepository.findFirstByAccountId(transactionDto.getToAccount());

        Transaction transaction = new Transaction(
                fromAccount.get(),
                toAccount.get(),
                transactionDto.getAmount(),
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                TransactionType.WITHDRAW,
                TransactionStatus.COMPLETED
                );

        transactionRepository.save(transaction);
    }

    public void createSchedulePayment(CreateTransactionDto transactionDto) {}

}
