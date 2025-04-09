package org.example.bankup.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException customerNotFound() {
        return new EntityNotFoundException("Customer not found");
    }

    public static EntityNotFoundException accountNotFound() {
        return new EntityNotFoundException("Account not found");
    }

    public static EntityNotFoundException transactionNotFound() {
        return new EntityNotFoundException("Transaction not found");
    }

    public static EntityNotFoundException loanNotFound() {
        return new EntityNotFoundException("Loan not found");
    }
}
