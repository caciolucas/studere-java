package com.studere.studerejava.framework.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAuthenticatedException extends BaseException {
    public NotAuthenticatedException(String message) {
        super(message);
    }
}
