package com.studere.studerejava.framework.core.handlers;

import com.studere.studerejava.framework.core.exceptions.RegisterEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegisterEmailException.class)
    public ResponseEntity<String> handleRegisterEmailException(RegisterEmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
