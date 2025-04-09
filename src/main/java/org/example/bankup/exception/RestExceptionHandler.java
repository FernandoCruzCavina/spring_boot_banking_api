package org.example.bankup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorMessageException> handleEntityNotFound(EntityNotFoundException ex) {

        ErrorMessageException responseException = new ErrorMessageException(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseException);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessageException> handleUnauthorized(UnauthorizedException ex) {

        ErrorMessageException responseException = new ErrorMessageException(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseException);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessageException> handleBusinessError(BusinessException ex) {

        ErrorMessageException responseException = new ErrorMessageException(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseException);
    }

}
