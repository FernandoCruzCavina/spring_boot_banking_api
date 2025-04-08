package org.example.bankup.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(){
        super("Account not found");
    }
}
