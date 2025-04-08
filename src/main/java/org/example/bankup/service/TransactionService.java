package org.example.bankup.service;

import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.constants.TransactionType;
import org.example.bankup.dto.transaction.CreateTransactionDto;
import org.example.bankup.dto.transaction.ViewTransactionDto;
import org.example.bankup.entity.Account;
import org.example.bankup.entity.Transaction;
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
        Optional<Account> fromAccountOpt = accountRepository.findFirstByAccountId(transactionDto.fromAccount().getAccountId());
        Optional<Account> toAccountOpt = accountRepository.findFirstByAccountId(transactionDto.toAccount().getAccountId());

        if(fromAccountOpt.isEmpty() || toAccountOpt.isEmpty()) throw new RuntimeException();

        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        boolean isValidPayment = verifyPayment(fromAccount, toAccount, transactionDto.amount());

        if (isValidPayment) {

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
        } else{
            return null;
        }
    }

    private void payment(Transaction transaction)  {

        Account fromAccount = transaction.getFromAccount();
        Account toAccount = transaction.getToAccount();

        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    private boolean verifyPayment(Account fromAccount, Account toAccount, double transactionAmount) {

        boolean isEnoughBalance = verifyBalance(fromAccount, transactionAmount);
        boolean isStatusActive = verifyStatus(fromAccount, toAccount);

        return isEnoughBalance && isStatusActive;
    }

    private boolean verifyBalance(Account fromAccount, double transactionAmount) {

        double balanceFromAccount = fromAccount.getBalance();

        return transactionAmount < balanceFromAccount;
    }

    private boolean verifyStatus(Account fromAccount, Account toAccount) {
        AccountStatus statusFromAccount = fromAccount.getStatus();
        AccountStatus statusToAccount = toAccount.getStatus();

        boolean isStatusValidFromAccount = statusFromAccount.equals(AccountStatus.ACTIVE);
        boolean isStatusValidToAccount = statusToAccount.equals(AccountStatus.ACTIVE);

        return isStatusValidFromAccount && isStatusValidToAccount;

    }
}