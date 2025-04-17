package org.example.bankup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;

    @Column(nullable = false, name = "amount")
    private double amount;

    @Column(nullable = false, name = "loan_date")
    private Timestamp loanDate;

    @ManyToOne
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;
}
