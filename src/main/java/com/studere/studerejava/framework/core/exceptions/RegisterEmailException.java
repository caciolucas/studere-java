package com.studere.studerejava.framework.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegisterEmailException extends BaseException {
    public RegisterEmailException(String message) {
        super(message);
    }
}
