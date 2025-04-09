package org.example.bankup.service;

import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.constants.TransactionType;
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

        return transaction.map(TransactionMapper.INSTANCE::transactionToViewTransactionDto).orElse(null);
    }

    public void createSchedulePayment(CreateTransactionDto transactionDto) {

    }

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
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                TransactionType.WITHDRAW,
                TransactionStatus.COMPLETED
        );

        payment(transaction);
        transactionRepository.save(transaction);

        return TransactionMapper.INSTANCE.transactionToViewTransactionDto(transaction);
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

}