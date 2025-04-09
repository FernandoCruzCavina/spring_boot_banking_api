package org.example.bankup.exception;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message) {
        super(message);
    }

    public static UnauthorizedException credentialsInvalid() {
        return new UnauthorizedException("Email or password is incorrect");
    }

    public static UnauthorizedException tokenExpired() {
        return new UnauthorizedException("Expired token");
    }

    public static UnauthorizedException accessDenied() {
        return new UnauthorizedException("Denied access");
    }
}
