package com.studere.studerejava.framework.models.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ValidationErrorResponseDTO extends ErrorResponseDTO {
    private final List<String> errors;

    public ValidationErrorResponseDTO(List<String> errors) {
        super("Validation failed", HttpStatus.BAD_REQUEST.value());
        this.errors = errors;
    }

}