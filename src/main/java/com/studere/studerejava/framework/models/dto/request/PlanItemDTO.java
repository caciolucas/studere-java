package com.studere.studerejava.framework.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PlanItemDTO {
    @NotBlank(message = "'title' is required")
    private String title;

    private String description;
}
