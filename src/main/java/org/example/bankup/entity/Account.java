package org.example.bankup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.AccountType;

import java.sql.Timestamp;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "bank", name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;

    @Column(nullable = false, name = "balance")
    private double balance;

    @Column(nullable = false, name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(nullable = false, name = "created_at")
    private Timestamp createdAt;

    public Account(Customer customer) {
        this.customer = customer;
    }

    public Account(double balance, AccountType accountType, AccountStatus status,Timestamp createdAt, Customer customer) {
        this.balance = balance;
        this.accountType = accountType;
        this.status = status;
        this.createdAt = createdAt;
        this.customer = customer;
    }
}
