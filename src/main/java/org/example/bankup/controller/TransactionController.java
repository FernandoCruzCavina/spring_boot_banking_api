package org.example.bankup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.bankup.dto.transaction.CreateScheduledTranscationDto;
import org.example.bankup.dto.transaction.CreateTransactionDto;
import org.example.bankup.dto.transaction.ViewTransactionDto;
import org.example.bankup.entity.Transaction;
import org.example.bankup.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@Tag( name = "Transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewTransactionDto> getTransactionById(@RequestParam int id) {
        ViewTransactionDto transaction = transactionService.getTransactionById(id);

        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/create")
    public ResponseEntity<ViewTransactionDto> createTransaction(@RequestBody CreateTransactionDto createTransactionDto) {
        ViewTransactionDto transaction = transactionService.createPaymentNow(createTransactionDto);

        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/create/schendule")
    public ResponseEntity<ViewTransactionDto> createTransactionSchendule(@RequestBody CreateScheduledTranscationDto createTransactionDto) {
        ViewTransactionDto transactionDto = transactionService.createSchedulePayment(createTransactionDto);

        return ResponseEntity.ok(transactionDto);
    }
}
