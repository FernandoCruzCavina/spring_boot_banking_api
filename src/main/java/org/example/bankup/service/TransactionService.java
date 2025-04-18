package org.example.bankup.service;

import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.constants.TransactionType;
import org.example.bankup.dto.transaction.CreateScheduledTranscationDto;
import org.example.bankup.dto.transaction.CreateTransactionDto;
import org.example.bankup.dto.transaction.ViewTransactionDto;
import org.example.bankup.entity.Account;
import org.example.bankup.entity.Transaction;
import org.example.bankup.exception.BusinessException;
import org.example.bankup.exception.EntityNotFoundException;
import org.example.bankup.mapper.TransactionMapper;
import org.example.bankup.repository.AccountRepository;
import org.example.bankup.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionMapper = transactionMapper;
    }

    @Cacheable(value = "transactions", key = "#id")
    public ViewTransactionDto getTransactionById(long id){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(EntityNotFoundException::accountNotFound);

        return transactionMapper.transactionToViewTransactionDto(transaction);
    }

    public ViewTransactionDto createSchedulePayment(CreateScheduledTranscationDto transactionDto) {

        Account fromAccount = accountRepository.findFirstByAccountId(
                transactionDto.fromAccount().getAccountId()
        ).orElseThrow(EntityNotFoundException::accountNotFound);

        Account toAccount = accountRepository.findFirstByAccountId(
                transactionDto.toAccount().getAccountId()
        ).orElseThrow(EntityNotFoundException::accountNotFound);

        verifyBalance(fromAccount, transactionDto.amount());
        verifyStatus(fromAccount, toAccount);
        verifyDate(transactionDto.createdTransactionDate());

        Transaction transaction = new Transaction(
                fromAccount,
                toAccount,
                transactionDto.amount(),
                transactionDto.createdTransactionDate(),
                transactionDto.completedTransactionDate(),
                transactionDto.transactionType(),
                TransactionStatus.PENDING
        );

        transactionRepository.save(transaction);

        return transactionMapper.transactionToViewTransactionDto(transaction);
    }

    @CachePut(value = "transactions", key = "#result.id()")
    public ViewTransactionDto createPaymentNow(CreateTransactionDto transactionDto) {

        Account fromAccount = accountRepository.findFirstByAccountId(
                transactionDto.fromAccount().getAccountId()
        ).orElseThrow(EntityNotFoundException::accountNotFound);

        Account toAccount = accountRepository.findFirstByAccountId(
                transactionDto.toAccount().getAccountId()
        ).orElseThrow(EntityNotFoundException::accountNotFound);

        verifyBalance(fromAccount, transactionDto.amount());
        verifyStatus(fromAccount, toAccount);

        Transaction transaction = new Transaction(
                fromAccount,
                toAccount,
                transactionDto.amount(),
                transactionDto.createdTransactionDate(),
                Timestamp.from(Instant.now()),
                transactionDto.transactionType(),
                TransactionStatus.COMPLETED
        );

        payment(transaction);
        transactionRepository.save(transaction);

        return transactionMapper.transactionToViewTransactionDto(transaction);
    }

    private void payment(Transaction transaction)  {

        Account fromAccount = transaction.getFromAccount();
        Account toAccount = transaction.getToAccount();

        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    private void verifyBalance(Account fromAccount, double amount) {
        if (amount <= 0) {
            throw BusinessException.invalidTransactionAmount();
        }

        if (fromAccount.getBalance() < amount) {
            throw BusinessException.insufficientBalance();
        }
    }


    private void verifyStatus(Account fromAccount, Account toAccount) {
        if (!fromAccount.getStatus().equals(AccountStatus.ACTIVE)) {
            throw BusinessException.statusInactive();
        }

        if (!toAccount.getStatus().equals(AccountStatus.ACTIVE)) {
            throw BusinessException.statusInactive();
        }
    }

    private void verifyDate(Timestamp transactionDate) {
        if(transactionDate.getTime() > Instant.now().getEpochSecond()){
            throw BusinessException.InvalidDate();
        }
    }

}