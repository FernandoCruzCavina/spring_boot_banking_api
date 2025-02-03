package org.example.bankup.service;

import org.example.bankup.dto.loan.LoanDto;
import org.example.bankup.entity.Loan;
import org.example.bankup.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void createLoan(LoanDto loanDto) {
        Loan loan = new Loan(
                loanDto.getId(),
                loanDto.getAmount(),
                loanDto.getDate(),
                loanDto.getAccount()
        );
    }
}
