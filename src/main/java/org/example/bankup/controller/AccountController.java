package org.example.bankup.controller;

import org.example.bankup.dto.account.CreateAccountDto;
import org.example.bankup.dto.account.ViewAccountDto;
import org.example.bankup.service.AccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewAccountDto> getAccountByCustomerId (@PathVariable long id) {
        ViewAccountDto viewAccountDto = accountService.getAccountByCustomerId(id);

        if (viewAccountDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(viewAccountDto);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountDto accountDto){

        accountService.createAccount(accountDto);
        return ResponseEntity.ok("Account created");
    }
}
