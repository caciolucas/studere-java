package com.studere.studerejava.framework.models.dto.request;

import jakarta.validation.constraints.NotBlank;

public class SessionUpdateDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;
}
