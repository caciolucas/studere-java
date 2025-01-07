package com.studere.studerejava.framework.models.dto;

import java.time.LocalDateTime;

public class ErrorResponseDTO {
    private String detail;
    private LocalDateTime timestamp;
    private int status;

    public ErrorResponseDTO(String detail, int status) {
        this.detail = detail;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }
}