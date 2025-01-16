package com.studere.studerejava.framework.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SessionStartDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @JsonProperty("plan_id")
    @NotBlank(message = "Plan ID is required")
    private UUID planId;
}
