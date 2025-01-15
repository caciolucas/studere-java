package com.studere.studerejava.framework.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ModuleCreateOrUpdateDTO {
    @NotBlank(message = "Email is required")
    private String name;

    private String description;
}
