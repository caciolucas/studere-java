package com.studere.studerejava.framework.models.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ValidationErrorResponseDTO extends ErrorResponseDTO {
    private List<String> errors;

    public ValidationErrorResponseDTO(List<String> errors) {
        super("Validation failed", HttpStatus.BAD_REQUEST.value());
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}