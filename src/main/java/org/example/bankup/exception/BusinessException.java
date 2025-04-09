package org.example.bankup.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }

    public static BusinessException insufficientBalance() {
        return new BusinessException("insufficient balance");
    }

    public static BusinessException loanAlreadyPaid() {
        return new BusinessException("Loan already paid");
    }

    public static BusinessException invalidTransactionAmount() {
        return new BusinessException("invalid transaction amount.");
    }

    public static BusinessException statusInactive() {
        return new BusinessException("status inactive");
    }
}
