package org.example.bankup.service;

import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.entity.Account;
import org.example.bankup.entity.Transaction;
import org.example.bankup.repository.AccountRepository;
import org.example.bankup.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class ScheduledPaymentProcessor {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public ScheduledPaymentProcessor(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void scheduledPaymentRunner() {
        List<Transaction> pendingTransactions = transactionRepository.findAllByStatusAndCompletedTransactionDateLessThanEqual(
                TransactionStatus.PENDING,
                Timestamp.from(Instant.now())
        );

        for (Transaction transaction : pendingTransactions) {
            processTransaction(transaction);
        }
    }

    private void processTransaction(Transaction transaction) {
        Account fromAccount = transaction.getFromAccount();
        Account toAccount = transaction.getToAccount();
        double amount = transaction.getAmount();

        if (fromAccount.getBalance() >= amount) {

            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            transaction.setStatus(TransactionStatus.COMPLETED);

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
        }

        transactionRepository.save(transaction);
    }
}
