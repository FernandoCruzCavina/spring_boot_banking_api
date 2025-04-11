package org.example.bankup.repository;

import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByStatusAndCompletedTransactionDateLessThanEqual(TransactionStatus transactionStatus, Timestamp transactionDate);
}
