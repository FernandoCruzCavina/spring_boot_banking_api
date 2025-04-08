package org.example.bankup.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(){
        super("Customer not found");
    }
}
