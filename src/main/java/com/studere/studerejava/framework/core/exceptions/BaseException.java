package com.studere.studerejava.framework.core.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
