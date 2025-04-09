package org.example.bankup.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessageException extends ResponseEntityExceptionHandler {

    private HttpStatus status;
    private String message;

}
