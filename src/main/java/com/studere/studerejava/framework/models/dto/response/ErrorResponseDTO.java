package com.studere.studerejava.framework.models.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponseDTO {
    private final String detail;
    private final LocalDateTime timestamp;
    private final int status;

    public ErrorResponseDTO(String detail, int status) {
        this.detail = detail;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

}