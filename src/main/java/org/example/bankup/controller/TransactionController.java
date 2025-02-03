package org.example.bankup.controller;

import org.example.bankup.dto.transaction.CreateTransactionDto;
import org.example.bankup.dto.transaction.ViewTransactionDto;
import org.example.bankup.entity.Transaction;
import org.example.bankup.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewTransactionDto> getTransactionById(@RequestParam int id) {
        ViewTransactionDto transaction = transactionService.getTransactionById(id);

        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTransaction(@RequestBody CreateTransactionDto createTransactionDto) {
        Transaction transaction = new Transaction();

        return ResponseEntity.ok("created transaction");
    }
}
