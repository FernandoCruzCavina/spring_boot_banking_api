package org.example.bankup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.bankup.dto.account.CreateAccountDto;
import org.example.bankup.dto.account.ViewAccountDto;
import org.example.bankup.service.AccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
            summary = "return viewAccountDto",
            responses = {
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized",
                            content = @Content(
                                    schema = @Schema(
                                            
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    )
            }
    )
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable long customerId){
        boolean isDeleted = accountService.deleteAccountByCustomerId(customerId);

        if (isDeleted) {
            return ResponseEntity.ok("Account deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
