package org.example.bankup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bankup.constants.TransactionStatus;
import org.example.bankup.constants.TransactionType;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "bank", name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    @Column(nullable = false, name = "amount")
    private double amount;

    @Column(nullable = false, name = "create_transaction_date")
    private Timestamp createdTransactionDate;

    @Column(nullable = false, name = "completed_transaction_date")
    private Timestamp completedTransactionDate;

    @Column(nullable = false, name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


    public Transaction(Account fromAccount, Account toAccount, double amount, Timestamp createdTransactionDate, Timestamp completedTransactionDate, TransactionType transactionType, TransactionStatus status) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.createdTransactionDate = createdTransactionDate;
        this.completedTransactionDate = completedTransactionDate;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
    }
}
